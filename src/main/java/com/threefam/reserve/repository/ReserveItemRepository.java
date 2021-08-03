package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.ReserveItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveItemRepository extends JpaRepository<ReserveItem, Long> {
}
