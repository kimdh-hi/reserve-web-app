package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableTime;

public interface AvailableTimeCustomRepository {

    AvailableTime findAvailableTimeById(Long timeId);

    AvailableTime findAvailableTimeByTimeAndDateId(Integer time, Long dateId);
}
