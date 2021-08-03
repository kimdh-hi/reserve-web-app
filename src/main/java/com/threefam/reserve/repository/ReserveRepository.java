package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
