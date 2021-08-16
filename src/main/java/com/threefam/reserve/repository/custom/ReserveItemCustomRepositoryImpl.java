package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.AvailableTime;
import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.Vaccine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveItemCustomRepositoryImpl implements ReserveItemCustomRepository {

    private final EntityManager em;

    @Override
    public List<AvailableDate> findAvailableDatesByHospitalId(Long id) {
        return em.createQuery(
                "select d " +
                        "from AvailableDate d " +
                        "where d.hospital.id = :id and d.enabled = true", AvailableDate.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<AvailableTime> findAvailableTimesByAvailableDateId(Long id) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime  t " +
                        "where t.availableDate.id = :id and t.enabled = true" , AvailableTime.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Vaccine> findAvailableVaccines(Long hospitalId) {
        return em.createQuery(
                "select v " +
                        "from Vaccine v " +
                        "where v.hospital.id = :hospitalId and v.quantity > 0 and v.enabled = true", Vaccine.class
        )
                .setParameter("hospitalId", hospitalId)
                .getResultList();
    }

    @Override
    public List<ReserveItem> findAllReserveItem(){
        return em.createQuery(
                "select distinct ri " +
                        "from ReserveItem ri " +
                        "join fetch ri.user u " +
                        "join fetch ri.Hospital h"
        ,ReserveItem.class)
                .getResultList();
    }
}
