package com.threefam.reserve.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveItemRequestDto {
    private Long hospitalId;
    private String vaccineName;
    private Long reserveDateId;
    private Long reserveTimeId;
}
