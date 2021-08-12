package com.threefam.reserve.dto.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VaccineReserveDto {

    private Long id;
    private String vaccineName;
}
