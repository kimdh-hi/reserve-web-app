package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.Vaccine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
@Repository
public class VaccineCustomRepository {

    private final EntityManager em;

    public Vaccine findVaccine(Long hospitalId, String vaccineName) {
        return em.createQuery(
                "select v " +
                        "from Vaccine v  join v.hospital h " +
                        "where h.id = :hospitalId and v.vaccineName = :vaccineName and v.enabled = true", Vaccine.class
        )
                .setParameter("hospitalId", hospitalId)
                .setParameter("vaccineName", vaccineName)
                .getSingleResult();
    }

    public Vaccine findVaccineDisabled(Long hospitalId, String vaccineName) {
        return em.createQuery(
                "select v " +
                        "from Vaccine v  join v.hospital h " +
                        "where h.id = :hospitalId and v.vaccineName = :vaccineName", Vaccine.class
        )
                .setParameter("hospitalId", hospitalId)
                .setParameter("vaccineName", vaccineName)
                .getSingleResult();
    }
}
