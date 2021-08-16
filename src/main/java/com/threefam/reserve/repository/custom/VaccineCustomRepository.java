package com.threefam.reserve.repository.custom;

import com.threefam.reserve.domain.entity.Vaccine;

public interface VaccineCustomRepository {

    Vaccine findVaccine(Long hospitalId, String vaccineName);

    Vaccine findVaccineDisabled(Long hospitalId, String vaccineName);
}
