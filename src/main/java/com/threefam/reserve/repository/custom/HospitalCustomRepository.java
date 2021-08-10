package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HospitalCustomRepository {

    private final EntityManager em;

    //  병원명, 병원주소, 백신잔여수량
    public List<HospitalListDto> findAllHospitalInfo(Long id) {
        return em.createQuery(
                "select new com.threefam.reserve.dto.hospital.HospitalListDto(h.hospitalName, h.address, h.totalQuantity) " +
                        "from Hospital h " +
                        "where h.admin.id = :id"
        , HospitalListDto.class).setParameter("id",id).getResultList();
    };
}
