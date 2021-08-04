package com.threefam.reserve.service;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.dto.HospitalRequestDto;
import com.threefam.reserve.dto.HospitalResponseDto;
import com.threefam.reserve.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final HospitalRepository hospitalRepository;

    /**
     * 병원 정보 등록
     */
    @Transactional
    @Override
    public Long addHospital(HospitalRequestDto hospitalRequestDto) {

        Hospital hospital = hospitalRequestDto.toEntityHospital();

        Map<String, Integer> vaccineInfoMap = hospitalRequestDto.getVaccineInfoMap();
        for (String key : vaccineInfoMap.keySet()) {
            Vaccine vaccine = Vaccine.createVaccine()
                    .vaccineName(key)
                    .quantity(vaccineInfoMap.get(key))
                    .build();
            vaccine.addHospital(hospital);
        }

        Hospital savedHospital = hospitalRepository.save(hospital);

        return savedHospital.getId();
    }

    /**
     * 병원이름으로 병원 정보 얻어오기
     */
    @Override
    public HospitalResponseDto getHospitalInfo(String hospitalName) {
        Hospital findHospital = hospitalRepository.findByHospitalName(hospitalName)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 병원입니다.");
                });
        List<Vaccine> vaccines = findHospital.getVaccines();
        Map<String, Integer> map = new HashMap<>();
        for (Vaccine vaccine : vaccines) {
            map.put(vaccine.getVaccineName(), vaccine.getQuantity());
        }

        return new HospitalResponseDto().createDto(
                findHospital.getHospitalName(),
                findHospital.getAvailableDates(),
                findHospital.getAvailableTimes(),
                findHospital.getAddress(),
                findHospital.getDetailAddress(),
                map
        );
    }
}
