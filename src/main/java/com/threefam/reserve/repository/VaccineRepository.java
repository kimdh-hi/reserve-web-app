package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
}
