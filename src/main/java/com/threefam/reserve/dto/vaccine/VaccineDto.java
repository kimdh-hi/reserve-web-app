package com.threefam.reserve.dto.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VaccineDto {

    private String vaccineName;
    private Integer quantity;
}
