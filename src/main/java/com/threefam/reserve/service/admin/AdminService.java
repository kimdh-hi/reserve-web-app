package com.threefam.reserve.service.admin;

import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;

public interface AdminService {

    Long addHospital(HospitalRequestDto hospitalAddDto);

    HospitalResponseDto getHospitalInfo(String hospitalName);
}
