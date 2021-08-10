package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import com.threefam.reserve.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * 병원 이름으로 병원 단건 조회
     */
    @GetMapping("/hospital")
    @ResponseBody
    public ResponseEntity<HospitalResponseDto> getHospital(@RequestParam("name") String hospitalName) {
        HospitalResponseDto hospitalResponseDto = adminService.getHospitalInfo(hospitalName);

        return ResponseEntity.ok(hospitalResponseDto);
    }

    /**
     * 병원 등록 폼 랜더링
     */
    @GetMapping("/add-hospital")
    public String hospitalForm(Model model){
        model.addAttribute("hospitalRequestDto",new HospitalRequestDto());
        return "admin/hospitalRegister";
    }

    /**
     * 현재 어드민이 관리하는 병원 목록 조회 (병원이름, 주소만 조회)
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
     * @param authentication 등록되는 병원애 admin을 추가해주기 위해 현재 인증 객체를 사용
     */
    @PostMapping("/add-hospital")
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
        return "redirect:/admin/list";
    }

    /**
     * 병원 목록
     */
    @GetMapping("/list")
    public String hospitalList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        String adminName = principal.getName();
        List<HospitalListDto> hospitalList = adminService.getHospitalList(adminName);
        model.addAttribute("hospitalList", hospitalList);
        return "admin/hospitalList";
    }

    @GetMapping("/hospital/{hospitalName}")
    public String hospitalInfo(Model model,@PathVariable("hospitalName")String name){
        log.info("hospital-name={}",name);
        HospitalRequestDto hospitalRequestDto = adminService.getHospital(name);
        model.addAttribute("hospitalRequestDto",hospitalRequestDto);

        return "admin/hospitalDetail";
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