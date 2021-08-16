package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AvailableDateCustomRepositoryImpl implements AvailableDateCustomRepository{

    private final EntityManager em;

    @Override
    public AvailableDate findAvailableDateByHospitalIdAndDate(Long hospitalId, String date) {
        return em.createQuery(
                "select d " +
                        "from AvailableDate d " +
                        "where d.hospital.id = :hospitalId and d.date = :date", AvailableDate.class
        )
                .setParameter("hospitalId", hospitalId)
                .setParameter("date", date)
                .getSingleResult();

    }
}
