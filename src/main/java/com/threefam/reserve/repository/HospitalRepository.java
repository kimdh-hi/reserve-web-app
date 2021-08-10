package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Admin;
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

    /**
     * 테스트용 조회 쿼리 (순환참조 막기 위해 일단 양방향 연관관계의 주인이 아닌 필드에  @JsonIgnoreProperties 설정해주었음
     */
    @Query("select h from Hospital h where h.admin = :admin")
    List<Hospital> findAllByAdmin(Admin admin);

}
