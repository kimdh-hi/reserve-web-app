package com.threefam.reserve.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    // Map<String, Integer> 형태로 데이터 받아보기
    @PostMapping("/listmap")
    public String listmapTest(@RequestBody TestVaccine testVaccine) {
        if (testVaccine != null) {
            List<String> vaccineNames = testVaccine.getVaccineNames();
            List<Integer> quantities = testVaccine.getQuantities();
            Map<String, Integer> vaccineInfoMap = testVaccine.getVaccineInfoMap();
            for (int i=0;i<vaccineNames.size();i++) {
                vaccineInfoMap.put(vaccineNames.get(i), quantities.get(i));
            }

            for (String s : vaccineInfoMap.keySet()) {
                log.info("vaccineName = {}, quantity = {}", s, vaccineInfoMap.get(s));
            }
        }
        return "ok";
    }

    @GetMapping("/test/ttt")
    public String adasd() {
        return "signUp_complete";
    }

    @Data
    static class TestVaccine{
        private List<String> vaccineNames = new ArrayList<>();
        private List<Integer> quantities = new ArrayList<>();
        private Map<String, Integer> vaccineInfoMap= new HashMap<>();
    }
}
