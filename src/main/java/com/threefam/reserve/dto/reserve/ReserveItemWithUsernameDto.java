package com.threefam.reserve.dto.reserve;

import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.value.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReserveItemWithUsernameDto {

    private Long reserveItemId;

    private String username;

    private String hospitalName;

    private String vaccineName;

    private String reserveDate;

    private Integer reserveTime;

    private ReserveStatus reserveStatus;

    public ReserveItemWithUsernameDto(ReserveItem reserveItem){
        this.reserveItemId = reserveItem.getId();
        this.username = reserveItem.getUser().getName();
        this.hospitalName = reserveItem.getHospital().getHospitalName();
        this.vaccineName = reserveItem.getVaccineName();
        this.reserveDate = reserveItem.getReserveDate();
        this.reserveTime = reserveItem.getReserveTime();
        this.reserveStatus = reserveItem.getStatus();
    }

}
