package com.threefam.reserve.repository;

import com.threefam.reserve.domain.ReserveItem;
import com.threefam.reserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveItemRepository extends JpaRepository<ReserveItem, Long> {
}
