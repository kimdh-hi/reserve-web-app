package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.dto.reserve.ReserveItemSimpleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReserveItemRepository extends JpaRepository<ReserveItem, Long> {

    @Query("select new com.threefam.reserve.dto.reserve.ReserveItemSimpleDto(ri.id, ri.Hospital.hospitalName, ri.vaccineName, ri.reserveDate, ri.reserveTime, ri.status) " +
            "from ReserveItem  ri " +
            "where ri.user.id = :userId")
    Optional<ReserveItemSimpleDto> findByUserId(Long userId);
}
