package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.Admin;
import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository{

    private final EntityManager em;

    @Override
    public List<HospitalListDto> findAllHospitalInfo(Long id) {
        return em.createQuery(
                "select new com.threefam.reserve.dto.hospital.HospitalListDto(h.id, h.hospitalName, h.address, h.totalQuantity) " +
                        "from Hospital h " +
                        "where h.admin.id = :id"
        , HospitalListDto.class).setParameter("id",id).getResultList();
    };

    /**
     * 병원아이디로 병원정보 조회
     */
    @Override
    public Optional<Hospital> findHospitalDetail(Long id){
        return Optional.of(em.createQuery(
                "select distinct h from Hospital h " +
                        "join fetch h.admin a " +
                        "join fetch h.vaccines v " +
                        "where h.id= :id",Hospital.class)
                .setParameter("id",id).getSingleResult());
    }

    /**
     * 예약가능 병원 조회 + 페이징
     */
    @Override
    public List<HospitalListDto> findHospitalListPaging(int offset, int limit) {
        return em.createQuery(
                "select new com.threefam.reserve.dto.hospital.HospitalListDto(h.id, h.hospitalName, h.address, h.totalQuantity) " +
                        "from Hospital h " +
                        "where h.enabled = true", HospitalListDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 주소로 예약가능 병원 조회 + 페이징
     */
    @Override
    public List<HospitalListDto> findHospitalListByAddressPaging(int offset, int limit, @Param("address") String address) {
        return em.createQuery(
                "select new com.threefam.reserve.dto.hospital.HospitalListDto(h.id, h.hospitalName, h.address, h.totalQuantity) " +
                        "from Hospital h " +
                        "where h.enabled = true and h.address like '%'||:address||'%'", HospitalListDto.class)
                .setParameter("address", address)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 주소, admin으로 병원 조회
     */
    @Override
    public List<HospitalListDto> findHospitalListByAddressAndAdmin(@Param("address") String address, Long adminId) {
        return em.createQuery(
                        "select new com.threefam.reserve.dto.hospital.HospitalListDto(h.id, h.hospitalName, h.address, h.totalQuantity) " +
                                "from Hospital h " +
                                "where h.admin.id= :adminId and h.address like '%'||:address||'%'", HospitalListDto.class)
                .setParameter("address", address)
                .setParameter("adminId",adminId)
                .getResultList();
    }
}
