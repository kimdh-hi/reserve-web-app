package com.threefam.reserve.dto.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HospitalListDto {

    private Long id;

    private String hospitalName;

    private String address;

    private Integer qty;
}
