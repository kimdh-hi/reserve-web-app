package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByHospitalName(String hospitalName);

    /**
     *  HospitalSimpleInfoDto 를 이용한 모든 병원의 이름, 주소 조회
     *  @return HospitalSimpleInfoDto
     */
    @Query("select new com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto(h.hospitalName,h.address) " +
            "from Hospital h " +
            "where h.enabled = true")
    List<HospitalSimpleInfoDto> findAllHospitalNameAndAddress();

}
