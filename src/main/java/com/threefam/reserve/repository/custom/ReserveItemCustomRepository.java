package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.AvailableTime;
import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.Vaccine;

import java.util.List;

public interface ReserveItemCustomRepository {

    /**
     * 예약가능 날짜 조회
     */
    List<AvailableDate> findAvailableDatesByHospitalId(Long id);

    /**
     * 예약가능 시간 조회
     */
    List<AvailableTime> findAvailableTimesByAvailableDateId(Long id);


    /**
     * 예약가능 백신이름 조회
     */
    List<Vaccine> findAvailableVaccines(Long hospitalId);

    /**
     * 예약 현황 조회
     */
    List<ReserveItem> findAllReserveItem(Long hospitalId);


}
