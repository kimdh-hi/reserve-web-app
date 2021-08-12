package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.reserve.AvailableDateDto;
import com.threefam.reserve.dto.reserve.AvailableTimeDto;
import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;
import com.threefam.reserve.dto.vaccine.VaccineReserveDto;
import com.threefam.reserve.repository.AvailableDateRepository;
import com.threefam.reserve.service.reserve.ReserveItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final AvailableDateRepository availableDateRepository;

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
    @GetMapping("/{hospitalName}/dates")
    public String getAvailableDates(@PathVariable String hospitalName, Model model) {
        // 병원이름으로 해당 병원의 예약가능날짜 조회
        List<AvailableDateDto> availableDates = reserveItemService.getAvailableDates(hospitalName);
        model.addAttribute("hospitalName", hospitalName);
        model.addAttribute("dates", availableDates);

        return "reserveDateSelectForm";
    }

    /**
     * 예약가능시간 조회 및 선택
     */
    @GetMapping("/{hospitalName}/times")
    public String getAvailableTimes(
            @PathVariable String hospitalName,
            @RequestParam(name="date") Long selectedDateId, Model model) {
        // 선택한 예약날짜의 pk로 예약가능시간 조회
        List<AvailableTimeDto> times = reserveItemService.getAvailableTimes(selectedDateId);
        //AvailableDate availableDate = availableDateRepository.findById(dateId).get();
        model.addAttribute("date", selectedDateId);
        model.addAttribute("times", times);
        return "reserveTimeSelectForm";
    }

    /**
     * 예약가능백신 조회 및 선택
     */
    @GetMapping("/{hospitalName}/vaccine")
    public String selectVaccine(
            @PathVariable String hospitalName,
            @RequestParam(name = "date") Long selectedDateId,
            @RequestParam(name = "time") Long selectedTimeId, Model model) {

        List<VaccineReserveDto> vaccines = reserveItemService.getAvailableVaccineNameList(hospitalName);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("date", selectedDateId);
        model.addAttribute("time", selectedTimeId);
        model.addAttribute("hospital", hospitalName);

        return "reserveVaccineSelectForm";
    }

    /**
     * 예약처리
     */
    @ResponseBody
    @PostMapping
    public String reserve(@ModelAttribute ReserveItemRequestDto reserveItemRequestDto) {
        log.info("hospitalName = {}", reserveItemRequestDto.getHospitalName());
        log.info("vaccineName = {}", reserveItemRequestDto.getVaccineName());
        log.info("reserveDate = {}", reserveItemRequestDto.getReserveDate());
        log.info("reserveTime = {}", reserveItemRequestDto.getReserveTime());

        return "ok";
    }
}
