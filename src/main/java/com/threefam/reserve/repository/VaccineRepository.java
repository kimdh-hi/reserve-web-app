package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.repository.custom.VaccineCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccineRepository extends JpaRepository<Vaccine, Long>, VaccineCustomRepository {

}
