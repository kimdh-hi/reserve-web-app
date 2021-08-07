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
    @NotEmpty(message = "병원이름을 입력해주세요.")
    private String hospitalName;

    @NotEmpty(message = "예약가능 시작날짜를 선택해주세요.")
    private String startDate;
    @NotEmpty(message = "예약가능 종료날짜를 선택해주세요.")
    private String endDate;
    @NotNull(message = "일일 최대 예약가능 인원을 입력해주세요.")
    private Integer dateAccept;

    @NotNull(message = "예약가능 시작시간을 선택해주세요.")
    private String startTime;
    @NotNull(message = "예약가능 종료시간을 선택해주세요.")
    private String endTime;
    @NotNull(message = "시간당 최대 예약가능 인원을 입력해주세요.")
    private Integer timeAccept;

    @NotEmpty(message = "병원 주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "병원 상세주소를 입력해주세요.")
    private String detailAddress;



    private List<String> vaccineNames = new ArrayList<>();

    private List<Integer> vaccineQuantities = new ArrayList<>();

    @NotNull(message = "수량을 입력해주세요.")
    private Integer astrazeneka;
    @NotNull(message = "수량을 입력해주세요.")
    private Integer janssen;
    @NotNull(message = "수량을 입력해주세요.")
    private Integer fizar;
    @NotNull(message = "수량을 입력해주세요.")
    private Integer modena;

    // 백신 종류마다 잔여수량을 달리하기 위해 Map 사용 (key:백신이름, value:잔여수령)
    private Map<String, Integer> vaccineInfoMap = new HashMap<>();

    public Hospital toHospitalEntity() {
        return Hospital.createHospital()
                .hospitalName(this.hospitalName)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .build();
    }
}


