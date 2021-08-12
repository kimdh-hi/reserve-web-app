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

@Data
@NoArgsConstructor
public class HospitalUpdateDto {

    private Long id;

    private String hospitalName;

    private String startDate;

    private String endDate;

    @NotNull(message = "일일 최대 예약가능 인원을 입력해주세요.")
    private Integer dateAccept;

    private String startTime;

    private String endTime;
    @NotNull(message = "시간당 최대 예약가능 인원을 입력해주세요.")
    private Integer timeAccept;

    private String address;
    private String detailAddress;


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

    @Builder(builderMethodName = "createHospitalUpdateDto")
    public HospitalUpdateDto(Long id,String hospitalName, String startDate, String endDate, Integer dateAccept,
                              String startTime, String endTime, Integer timeAccept, String address,
                              String detailAddress, Integer astrazeneka,
                              Integer janssen, Integer fizar, Integer modena) {
        this.id=id;
        this.hospitalName = hospitalName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateAccept = dateAccept;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeAccept = timeAccept;
        this.address = address;
        this.detailAddress = detailAddress;
        this.astrazeneka = astrazeneka;
        this.janssen = janssen;
        this.fizar = fizar;
        this.modena = modena;

    }
}
