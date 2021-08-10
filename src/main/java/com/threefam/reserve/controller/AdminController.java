package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/api/admin/hospital/{hospitalName}")
    @ResponseBody
    public ResponseEntity<HospitalResponseDto> getHospital(@PathVariable String hospitalName) {
        HospitalResponseDto hospitalResponseDto = adminService.getHospitalInfo(hospitalName);

        return ResponseEntity.ok(hospitalResponseDto);
    }

    @GetMapping("/admin/add-hospital")
    public String hospitalForm(Model model){
        model.addAttribute("hospitalRequestDto",new HospitalRequestDto());
        return "admin/hospitalRegister";
    }

    /**
     * 어드민으로 병원 조회 테스트 (병원 등록하고 접근해보면 Json으로 보일꺼임 근데 문제 많음 .. DTO로 찍어서 해줘여할 듯)
     */
    @ResponseBody
    @GetMapping("/admin/hospitals")
    public List<Hospital> asd(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        List<Hospital> hospitals = adminService.getAllHospitalInfo(principal.getName());
        return hospitals;
    }

    // 테스트
    @PostMapping("/admin/add-hospital")
    public String addHospital(
            Authentication authentication,
            @Validated @ModelAttribute HospitalRequestDto form, BindingResult result, HttpServletRequest request) throws Exception{

        if(result.hasErrors()){
            return "admin/hospitalRegister";
        }

        makeVaccineInfoMap(form.getAstrazeneka(), form.getJanssen(), form.getFizar(), form.getModena(), form);

        timeParse(form);
        /**
         * /admin/** 으로 접근되었다는 것은 security filter를 지나 인가된 사용자라는 것. (Role = ADMIN)
         * 따라서 병원 등록시 Authentication에서 얻어온 유저 정보를 그대로 사용 (병원에 Admin을 넣어주기 위함)
         */
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal.name = {}", principal.getName());

        adminService.addHospital(form, principal.getName());

        //일단은 홈으로 리턴 추후에 바꾸면 될듯
        //예약 리스트로 redirect (어드민 Hospital List, Hospital Detail List 필요)
        return "redirect:/admin/hospitals";
    }

    // 시간을 parseInt 되도록 만드는 메서드
    private void timeParse(HospitalRequestDto form) {
        form.setStartTime(form.getStartTime().split(":")[0]);
        form.setEndTime(form.getEndTime().split(":")[0]);
    }

    // vaccineInfoMap만드는 메서드
    private void makeVaccineInfoMap(Integer astrazeneka, Integer janssen, Integer fizar, Integer modena, HospitalRequestDto form) {
        Map<String,Integer> vaccineInfoMap= form.getVaccineInfoMap();
        if(astrazeneka !=null && astrazeneka !=0){
            vaccineInfoMap.put("아스트라제네카", astrazeneka);
        }
        if(janssen !=null && janssen !=0){
            vaccineInfoMap.put("얀센", janssen);
        }
        if(fizar !=null && fizar !=0){
            vaccineInfoMap.put("화이자", fizar);
        }
        if(modena !=null && modena !=0){
            vaccineInfoMap.put("모더나", modena);
        }
    }

}
