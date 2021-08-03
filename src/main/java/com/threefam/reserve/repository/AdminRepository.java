package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
