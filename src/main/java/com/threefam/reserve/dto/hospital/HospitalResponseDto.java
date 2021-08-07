package com.threefam.reserve.dto.hospital;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 병원 조회 결과를 위한 dto
 * json 포맷을 빈 값 없이 맞추기 위해 HospitalRequestDto와 따로 사용
 */

@Data
@NoArgsConstructor
public class HospitalResponseDto {
    private String hospitalName;
    private List<String> availableDates;
    private String address;
    private String detailAddress;

    private Map<String, Integer> vaccineInfoMap = new HashMap<>();

    public HospitalResponseDto createDto(String hospitalName, List<String> availableDates,
                                 String address, String detailAddress, Map<String, Integer> vaccineInfoMap) {
        this.hospitalName = hospitalName;
        this.availableDates = availableDates;
        this.address = address;
        this.detailAddress = detailAddress;
        this.vaccineInfoMap = vaccineInfoMap;

        return this;
    }
}
