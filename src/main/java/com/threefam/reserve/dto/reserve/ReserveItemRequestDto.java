package com.threefam.reserve.dto.reserve;

import lombok.Data;

@Data
public class ReserveItemRequestDto {

    private String hospitalName;
    private String vaccineName;
    private String reserveDate;
    private String reserveTime;
}
