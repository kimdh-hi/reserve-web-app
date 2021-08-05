package com.threefam.reserve.dto.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalSimpleInfoDto {

    private String hospitalName;
    private String address;
}
