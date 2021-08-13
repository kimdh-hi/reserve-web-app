package com.threefam.reserve.controller;

import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.reserve.AvailableDateDto;
import com.threefam.reserve.dto.reserve.AvailableTimeDto;
import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;
import com.threefam.reserve.dto.reserve.ReserveItemSimpleDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.dto.vaccine.VaccineReserveDto;
import com.threefam.reserve.service.reserve.ReserveItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * 예약가능날짜 조회 및 선택
     */
    @GetMapping("/{hospitalId}/dates")
    public String getAvailableDates(@PathVariable Long hospitalId, Model model) {
        // 병원이름으로 해당 병원의 예약가능날짜 조회
        List<AvailableDateDto> availableDates = reserveItemService.getAvailableDates(hospitalId);
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("dates", availableDates);

        return "user/reserve/reserveDateSelectForm";
    }

    /**
     * 예약가능시간 조회 및 선택
     */
    @GetMapping("/{hospitalId}/times")
    public String getAvailableTimes(
            @PathVariable Long hospitalId,
            @RequestParam(name="date") Long selectedDateId, Model model) {
        // 선택한 예약날짜의 pk로 예약가능시간 조회
        List<AvailableTimeDto> times = reserveItemService.getAvailableTimes(selectedDateId);
        model.addAttribute("date", selectedDateId);
        model.addAttribute("times", times);
        return "user/reserve/reserveTimeSelectForm";
    }

    /**
     * 예약가능백신 조회 및 선택
     */
    @GetMapping("/{hospitalId}/vaccine")
    public String selectVaccine(
            @PathVariable Long hospitalId,
            @RequestParam(name = "date") Long selectedDateId,
            @RequestParam(name = "time") Long selectedTimeId, Model model) {

        List<VaccineReserveDto> vaccines = reserveItemService.getAvailableVaccineNameList(hospitalId);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("date", selectedDateId);
        model.addAttribute("time", selectedTimeId);
        model.addAttribute("hospitalId", hospitalId);

        return "user/reserve/reserveVaccineSelectForm";
    }

    /**
     * 예약처리
     */
    @PostMapping
    public String reserve(
            @AuthenticationPrincipal PrincipalDetails principal,
            @ModelAttribute ReserveItemRequestDto reserveItemRequestDto,
            RedirectAttributes redirectAttributes) {
        log.info("hospitalId = {}", reserveItemRequestDto.getHospitalId());
        log.info("vaccineName = {}", reserveItemRequestDto.getVaccineName());
        log.info("reserveDateId = {}", reserveItemRequestDto.getReserveDateId());
        log.info("reserveTimeId = {}", reserveItemRequestDto.getReserveTimeId());

        String username = principal.getUsername();
        log.info("username = {}", username);

        Long savedUserId = reserveItemService.reserve(
                username,
                reserveItemRequestDto.getHospitalId(),
                reserveItemRequestDto.getVaccineName(),
                reserveItemRequestDto.getReserveDateId(),
                reserveItemRequestDto.getReserveTimeId()
        );

        redirectAttributes.addAttribute("userId", savedUserId);
        return "redirect:/reserve/{userId}";
    }

    @GetMapping("/{userId}")
    public String reserveResult(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        String username = principal.getUsername();
        log.info("username = {}", username);
        ReserveItemSimpleDto reserveResult = reserveItemService.getReserveResult(username);

        model.addAttribute("reserveResult", reserveResult);
        return "user/reserve/ReserveResult";
    }
}
