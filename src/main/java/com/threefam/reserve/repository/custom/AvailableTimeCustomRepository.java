package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AvailableTimeCustomRepository {

    private final EntityManager em;

    public AvailableTime findAvailableTimeById(Long timeId) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime t " +
                        "where t.id = :timeId and t.enabled=true", AvailableTime.class
        )
                .setParameter("timeId", timeId)
                .getSingleResult();
    }
}
