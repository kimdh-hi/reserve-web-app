package com.threefam.reserve.controller;

import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/api/admin/add")
    @ResponseBody
    public ResponseEntity<String> addHospital(@RequestBody HospitalRequestDto hospitalRequestDto) {
        // List 로 전달받은 백신이름과 잔여수량을 Map 으로 변환하여 dto에 넣어준다.
        if (hospitalRequestDto.getVaccineNames() != null && hospitalRequestDto.getVaccineQuantities() != null) {
            List<String> vaccineNames = hospitalRequestDto.getVaccineNames();
            List<Integer> vaccineQuantities = hospitalRequestDto.getVaccineQuantities();
            Map<String, Integer> vaccineInfoMap = hospitalRequestDto.getVaccineInfoMap();
            for (int i=0;i<vaccineNames.size();i++) {
                vaccineInfoMap.put(vaccineNames.get(i), vaccineQuantities.get(i));
            }
            // test log
            for (String key : vaccineInfoMap.keySet()) {
                log.info("vaccineName = {}, vaccineQuantity = {}", key, vaccineInfoMap.get(key));
            }
        }

        adminService.addHospital(hospitalRequestDto);

        return ResponseEntity.ok(hospitalRequestDto.getHospitalName() + "등록 완료");
    }

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

    //list를 화면으로 넘겨주려면 modelAttribute를 사용하여 따로 보내 줘야하는데 너무 번거로운 작업이기에 RequestParam으로 받아옴.(null이나 0이면 백신 추가x)
    @PostMapping("/admin/add-hospital")
    public String addHospital(
            @Validated @ModelAttribute HospitalRequestDto form, BindingResult result){

        if(result.hasErrors()){
            return "admin/hospitalRegister";
        }

        makeVaccineInfoMap(form.getAstrazeneka(), form.getJanssen(), form.getFizar(), form.getModena(), form);
        timeParse(form);
        adminService.addHospital(form);

        //일단은 홈으로 리턴 추후에 바꾸면 될듯
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
