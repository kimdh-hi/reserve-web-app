package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Admin;
import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalCustomRepository {

    Optional<Hospital> findByHospitalName(String hospitalName);

    /**
     *  HospitalSimpleInfoDto 를 이용한 모든 병원의 이름, 주소 조회
     *  @return HospitalSimpleInfoDto
     */
    @Query("select new com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto(h.hospitalName,h.address) " +
            "from Hospital h " +
            "where h.enabled = true")
    List<HospitalSimpleInfoDto> findAllHospitalNameAndAddress();

    /**
     * 어드민이 관리하는 모든 병원 정보 조회 (병원이름, 장소)
     * 어드민이 등록한 모든 병원의 간단한 정보만을 조회하기 위한 쿼리
     */
    @Query("select new com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto(h.hospitalName, h.address) " +
            "from Hospital h " +
            "where h.admin = :admin")
    List<HospitalSimpleInfoDto> findAllByAdmin(Admin admin);
}
