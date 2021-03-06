package com.threefam.reserve.controller;

import com.threefam.reserve.dto.hospital.*;
import com.threefam.reserve.dto.reserve.ReserveItemWithUsernameDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
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
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/hospital/add")
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
    @PostMapping("/hospital/add")
    public String addHospital(
            Authentication authentication,
            @Validated @ModelAttribute HospitalRequestDto form, BindingResult result, HttpServletRequest request) throws Exception{

        if(result.hasErrors()){
            return "admin/hospitalRegister";
        }

        makeVaccineInfoMap(form.getAstrazeneka(), form.getJanssen(), form.getFizar(), form.getModena(), form.getVaccineInfoMap());

        timeParse(form);
        /**
         * /admin/** 으로 접근되었다는 것은 security filter를 지나 인가된 사용자라는 것. (Role = ADMIN)
         * 따라서 병원 등록시 Authentication에서 얻어온 유저 정보를 그대로 사용 (병원에 Admin을 넣어주기 위함)
         */
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal.name = {}", principal.getName());

        adminService.addHospital(form, principal.getName());

        return "redirect:/admin/hospital/list";
    }

    /**
     * 병원 목록
     */
    @GetMapping("/hospital/list")
    public String hospitalList(@AuthenticationPrincipal PrincipalDetails principal, Model model,
                               @RequestParam(defaultValue = "noSearch")String addressSearch) {
        String adminName = principal.getName();
        List<HospitalListDto> hospitalList = adminService.getHospitalList(adminName,addressSearch);
        model.addAttribute("hospitalList", hospitalList);
        return "admin/hospitalList";
    }

    /**
     * 병원 상세정보 조회
     */
    @GetMapping("/hospital/{hospitalId}")
    public String hospitalInfo(Model model,@PathVariable("hospitalId")Long id){

        HospitalUpdateDto hospitalUpdateDto = adminService.getHospital(id);
        model.addAttribute("hospitalUpdateDto",hospitalUpdateDto);

        return "admin/hospitalDetail";
    }

    /**
     * 병원 수정
     */
    @PostMapping("/hospital/" +
            "edit/{hospitalId}")
    public String hospitalEdit(@PathVariable Long hospitalId,
            @Validated @ModelAttribute HospitalUpdateDto hospitalUpdateDto,BindingResult result)
            throws ParseException {
        if(result.hasErrors()){
            return "admin/hospitalDetail";
        }
        hospitalUpdateDto.setId(hospitalId);
        makeVaccineInfoMap(hospitalUpdateDto.getAstrazeneka(), hospitalUpdateDto.getJanssen(),
                hospitalUpdateDto.getFizar(), hospitalUpdateDto.getModena(), hospitalUpdateDto.getVaccineInfoMap());
        adminService.hospitalUpdate(hospitalUpdateDto);

        return "redirect:/admin/hospital/list";
    }

    /**
     * 예약 현황 조회
     */
    @GetMapping("/hospital/reserves/{hospitalId}")
    public String reserveCondition(@PathVariable Long hospitalId, Model model){
        List<ReserveItemWithUsernameDto> reserveItemConditions = adminService.getReserveItemCondition(hospitalId);

        model.addAttribute("reserveItemConditions",reserveItemConditions);
        return "admin/reserveCondition";
    }

    // 시간을 parseInt 되도록 만드는 메서드
    private void timeParse(HospitalRequestDto form) {
        form.setStartTime(form.getStartTime().split(":")[0]);
        form.setEndTime(form.getEndTime().split(":")[0]);
    }

    // vaccineInfoMap만드는 메서드
    private void makeVaccineInfoMap(Integer astrazeneka, Integer janssen, Integer fizar, Integer modena, Map<String,Integer> vaccineInfoMap) {

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