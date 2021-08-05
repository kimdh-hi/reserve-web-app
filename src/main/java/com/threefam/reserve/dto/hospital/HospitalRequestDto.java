package com.threefam.reserve.dto.hospital;

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

/**
 * 어드민으로부터 병원등록 요청을 처리하기 위한 DTO
 */

@Data
@NoArgsConstructor
public class HospitalRequestDto {
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
    // 백신이름, 잔여수량
    @NotEmpty(message = "백신 이름을 입력해주세요.")
    private List<String> vaccineNames = new ArrayList<>();
    @NotNull(message = "백신 예약가능 수량을 입력해주세요.")
    private List<Integer> vaccineQuantities = new ArrayList<>();
    // 백신 종류마다 잔여수량을 달리하기 위해 Map 사용 (key:백신이름, value:잔여수령)
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
}


