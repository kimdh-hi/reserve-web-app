package com.threefam.reserve.service;

import com.threefam.reserve.dto.HospitalRequestDto;
import com.threefam.reserve.dto.HospitalResponseDto;

public interface AdminService {

    Long addHospital(HospitalRequestDto hospitalAddDto);

    HospitalResponseDto getHospitalInfo(String hospitalName);
}
