package com.threefam.reserve.dto.reserve;

import com.threefam.reserve.domain.value.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveItemSimpleDto {
    private String hospitalName;

    private String vaccineName;

    private String reserveDate;

    private Integer reserveTime;

    private ReserveStatus reserveStatus;
}
