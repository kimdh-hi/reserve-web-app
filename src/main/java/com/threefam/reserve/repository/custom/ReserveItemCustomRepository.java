package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.AvailableTime;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveItemCustomRepository {

    private final EntityManager em;

    /**
     * 예약가능 날짜 조회
     */
    public List<AvailableDate> findAvailableDatesByHospitalId(Long id) {
        return em.createQuery(
                "select d " +
                        "from AvailableDate d " +
                        "where d.hospital.id = :id and d.enabled = true", AvailableDate.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * 예약가능 시간 조회
     */
    public List<AvailableTime> findAvailableTimesByAvailableDateId(Long id) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime  t " +
                        "where t.availableDate.id = :id and t.enabled = true" , AvailableTime.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * 예약가능 백신이름 조회
     */
    public List<Vaccine> findAvailableVaccines(Long hospitalId) {
        return em.createQuery(
                "select v " +
                        "from Vaccine v " +
                        "where v.hospital.id = :hospitalId and v.quantity > 0 and v.enabled = true", Vaccine.class
        )
                .setParameter("hospitalId", hospitalId)
                .getResultList();
    }
}
