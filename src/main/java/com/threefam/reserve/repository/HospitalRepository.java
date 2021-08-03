package com.threefam.reserve.repository;

import com.threefam.reserve.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
