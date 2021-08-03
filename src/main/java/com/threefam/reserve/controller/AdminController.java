package com.threefam.reserve.controller;

import com.threefam.reserve.dto.HospitalAddDto;
import com.threefam.reserve.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/api/admin/add")
    public String addHospital(@RequestBody HospitalAddDto hospitalAddDto) {
        adminService.addHospital(hospitalAddDto);

        return "OK";
    }
}
