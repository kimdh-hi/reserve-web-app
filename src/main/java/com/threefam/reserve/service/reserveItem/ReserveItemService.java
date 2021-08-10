package com.threefam.reserve.service.reserveItem;

import com.threefam.reserve.dto.hospital.HospitalListDto;

import java.util.List;

public interface ReserveItemService {

    List<HospitalListDto> getAllHospitalInfo(int offset, int limit);

    void getReserveHospitalInfo(String hospitalName);

}
