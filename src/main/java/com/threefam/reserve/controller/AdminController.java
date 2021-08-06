package com.threefam.reserve.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    /**
     * GET: 예약 등록 폼
     */
    @GetMapping("/admin/add")
    public String addForm() {
        return "register";
    }
}
