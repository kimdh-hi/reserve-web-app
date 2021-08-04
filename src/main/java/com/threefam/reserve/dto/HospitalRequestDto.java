package com.threefam.reserve.dto;

import com.threefam.reserve.domain.entity.Hospital;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class HospitaRequestlDto {
    // 병원이름, 예약가능날짜, 예약가능시간, 주소, 상세주소
    @NotEmpty(message = "병원이름을 입력해주세요.")
    private String hospitalName;
    @NotNull(message = "예약가능 날짜를 선택해주세요.")
    private List<String> availableDates;
    @NotNull(message = "예약가능 시간을 선택해주세요.")
    private List<String> availableTimes;
    @NotEmpty(message = "병원 주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "병원 상세주소를 입력해주세요.")
    private String detailAddress;
    // 백신이름, 백신양,
    @NotEmpty(message = "백신 이름을 입력해주세요.")
    private List<String> vaccineNames = new ArrayList<>();
    @NotNull(message = "백신 예약가능 수량을 입력해주세요.")
    private List<Integer> vaccineQuantities = new ArrayList<>();

    private Map<String, Integer> vaccineInfoMap = new HashMap<>();

    public Hospital toEntityHospital() {
        return Hospital.createHospital()
                .hospitalName(this.hospitalName)
                .availableDates(this.availableDates)
                .availableTimes(this.availableTimes)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .build();
    }

    @Builder(builderMethodName = "createHospitalDto")
    public HospitaRequestlDto(String hospitalName, List<String> availableDates, List<String> availableTimes,
                              String address, String detailAddress) {
        this.hospitalName = hospitalName;
        this.availableDates = availableDates;
        this.availableTimes = availableTimes;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public HospitaRequestlDto createDto(String hospitalName, List<String> availableDates, List<String> availableTimes,
                                        String address, String detailAddress, Map<String, Integer> vaccineInfoMap) {
        this.hospitalName = hospitalName;
        this.availableDates = availableDates;
        this.availableTimes = availableTimes;
        this.address = address;
        this.detailAddress = detailAddress;
        this.vaccineInfoMap = vaccineInfoMap;

        return this;
    }
}

//    @NotEmpty(message = "백신 이름을 입력해주세요.")
//    private String vaccineName;
//    @NotNull(message = "백신 예약가능 수량을 입력해주세요.")
//    @Range(min = 0, message = "백신 예약가능 수량은 1개 이상부터 등록 가능합니다.")
//    private Integer quantity;

