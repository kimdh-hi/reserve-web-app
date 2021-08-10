package com.threefam.reserve.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveItemRequestDto {
    @NotEmpty(message = "병원명을 입력해주세요.")
    private String hospitalName;
    @NotNull(message = "백신을 선택해주세요.")
    private String vaccineName;
    @NotNull(message = "예약날짜를 선택해주세요.")
    private String reserveDate;
    @NotNull(message = "예약시간을 선택해주세요.")
    private String reserveTime;
}
