package com.threefam.reserve.repository;

import com.threefam.reserve.domain.Reserve;
import com.threefam.reserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
