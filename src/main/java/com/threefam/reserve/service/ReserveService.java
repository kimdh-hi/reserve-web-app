package com.threefam.reserve.service;

import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;

public interface ReserveService {

    Long reserve(ReserveItemRequestDto reserveItemRequestDto);
}
