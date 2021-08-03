package com.threefam.reserve.service;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.VaccineRepository;
import com.threefam.reserve.dto.HospitalAddDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final HospitalRepository hospitalRepository;
    private final VaccineRepository vaccineRepository;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public Long addHospital(HospitalAddDto hospitalAddDto) {

        Hospital hospital = hospitalAddDto.toEntityHospital();
        Vaccine vaccine = Vaccine.createVaccine()
                .vaccineName(hospitalAddDto.getVaccineName())
                .quantity(hospitalAddDto.getQuantity())
                .hospital(hospital)
                .build();
        vaccine.addHospital(hospital);
        Hospital savedHospital = hospitalRepository.save(hospital);

        return savedHospital.getId();
    }
}
