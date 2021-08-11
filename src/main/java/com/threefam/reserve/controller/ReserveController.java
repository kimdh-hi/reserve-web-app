package com.threefam.reserve.controller;

import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.service.reserve.ReserveItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveItemService reserveItemService;

    /**
     * 예약가능 병원 조회
     */
    @GetMapping("/hospitals")
    public String hospitalList(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit, Model model) {
        List<HospitalListDto> hospitalListDtos = reserveItemService.getAllHospitalInfo(offset, limit);
        model.addAttribute("hospitalList", hospitalListDtos);
        return "user/reserve/hospitalList";
    }

    /**
     * 예약 폼 (예약한 필요한 병원 정보는 쿼리파라미터로 받은 병원이름으로 찾아줌)
     */
    @GetMapping
    public String reserveForm(Model model, @RequestParam("name") String hospitalName) {
        
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
