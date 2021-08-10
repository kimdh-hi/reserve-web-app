package com.threefam.reserve.service.admin;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;

import java.util.List;

public interface AdminService {

    /**
     * 병원등록
     */
    Long addHospital(HospitalRequestDto hospitalAddDto,String adminName) throws Exception;


    /**
     * 병원이름으로 병원 단건 조회
     */
    HospitalResponseDto getHospitalInfo(String hospitalName);

    /**
     * 어드민이 관리하는 병원 리스트를 보여주기 위한 메서드
     */
    List<HospitalSimpleInfoDto> getAllSimpleHospitalInfo(String name);
}
