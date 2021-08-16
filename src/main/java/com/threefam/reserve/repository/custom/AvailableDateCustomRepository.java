package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableDate;

public interface AvailableDateCustomRepository {

    AvailableDate findAvailableDateByHospitalIdAndDate(Long hospitalId, String date);
}
