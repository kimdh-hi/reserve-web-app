package com.threefam.reserve.controller;

import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/api/admin/add")
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
    public ResponseEntity<HospitalResponseDto> getHospital(@PathVariable String hospitalName) {
        HospitalResponseDto hospitalResponseDto = adminService.getHospitalInfo(hospitalName);

        return ResponseEntity.ok(hospitalResponseDto);
    }

}
