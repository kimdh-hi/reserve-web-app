package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalCustomRepository {

    List<HospitalListDto> findAllHospitalInfo(Long id);

    Optional<Hospital> findHospitalDetail(Long id);

    List<HospitalListDto> findHospitalListPaging(int offset, int limit);

    List<HospitalListDto> findHospitalListByAddressPaging(int offset, int limit, @Param("address") String address);

    List<HospitalListDto> findHospitalListByAddressAndAdmin(@Param("address") String address, Long adminId);

}
