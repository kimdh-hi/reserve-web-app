package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @GetMapping("/admin/session-test")
    public String sessionTest(Authentication authentication) {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal.name = {}", principal.getName());
        return "redirect:/";
    }

    //list를 화면으로 넘겨주려면 modelAttribute를 사용하여 따로 보내 줘야하는데 너무 번거로운 작업이기에 RequestParam으로 받아옴.(null이나 0이면 백신 추가x)
    @PostMapping("/admin/add-hospital")
    public String addHospital(
            Authentication authentication,
            @Validated @ModelAttribute HospitalRequestDto form, BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return "admin/hospitalRegister";
        }

        makeVaccineInfoMap(form.getAstrazeneka(), form.getJanssen(), form.getFizar(), form.getModena(), form);
        timeParse(form);

        adminService.addHospital(form);

        //일단은 홈으로 리턴 추후에 바꾸면 될듯
        //예약 리스트로 redirect (어드민 Hospital List, Hospital Detail List 필요)
        return "redirect:/";
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
