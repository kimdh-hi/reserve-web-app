package com.threefam.reserve.controller;

import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    /**
     * 예약 폼
     */
    @GetMapping("/{hospitalName}")
    public String reserveForm(Model model, @PathVariable String hospitalName) {
        model.addAttribute("form", new ReserveItemRequestDto());
        return "/user/reserve/reserveForm";
    }

    /**
     * 예약 처리
     */
    @PostMapping
    public String reserve(
            @AuthenticationPrincipal PrincipalDetails principal,
            @ModelAttribute ReserveItemRequestDto reserveItemRequestDto) {

        return "";
    }
}
