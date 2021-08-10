package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
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
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * 병원 등록 폼
     */
    @GetMapping("/add-hospital")
    public String hospitalForm(Model model){
        model.addAttribute("hospitalRequestDto",new HospitalRequestDto());
        return "admin/hospitalRegister";
    }

    /**
     * 어드민이 관리하는 모든 병원 목록 반환 (간단한 정보만 - 병원이름, 주소)
     */
    @ResponseBody
    @GetMapping("/hospitals")
    public List<HospitalSimpleInfoDto> asd(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        List<HospitalSimpleInfoDto> hospitals = adminService.getAllSimpleHospitalInfo(principal.getName());
        return hospitals;
    }

    /**
     * 병원 등록
     * @param authentication 현재 인증받은 사용자는 어드민이므로 등록하는 병원의 어드민이 되도록 설정하기 위함
     */
    @PostMapping("/add-hospital")
    public String addHospital(
            Authentication authentication,
            @Validated @ModelAttribute HospitalRequestDto form, BindingResult result){

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

        return "redirect:/admin/hospitals";
    }

    /**
     * 병원 상세 페이지
     */
    @ResponseBody // 테스트용
    @GetMapping("/hospital")
    public ResponseEntity<HospitalResponseDto> hospitalDetailPage(@RequestParam("name") String hospitalName) {
        HospitalResponseDto hospitalInfo = adminService.getHospitalInfo(hospitalName);
        return ResponseEntity.ok(hospitalInfo);
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
