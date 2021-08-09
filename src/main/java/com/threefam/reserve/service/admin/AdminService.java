package com.threefam.reserve.service.admin;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;

import java.util.List;

public interface AdminService {

    /**
     * 병원등록
     */
    Long addHospital(HospitalRequestDto hospitalAddDto, String adminName);

    /**
     * 병원이름으로 병원 단건 조회
     */
    HospitalResponseDto getHospitalInfo(String hospitalName);

    /**
     * 테스트용 어드민으로 병원정보 조회 서비스 메서드
     */
    List<Hospital> getAllHospitalInfo(String name);
}
