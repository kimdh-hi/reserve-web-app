package com.threefam.reserve.service;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.repository.HospitalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AdminServiceTest {

    @Autowired private HospitalRepository hospitalRepository;

    @Test
    @Rollback(false)
    void 병원_등록() {
        Hospital hospital = Hospital.createHospital()
                .hospitalName("좋은병원")
                .address("서울시 강서구")
                .detailAddress("좋은빌딩")
                .availableDates(List.of("2021/1/1", "2021/1/2", "2021/1/3"))
                .availableTimes(List.of("10:00", "13:00", "14:00"))
                .build();
        Vaccine vaccine1 = Vaccine.createVaccine()
                .vaccineName("화이자")
                .quantity(100)
                .build();
        vaccine1.addHospital(hospital);
        Vaccine vaccine2 = Vaccine.createVaccine()
                .vaccineName("AZ")
                .quantity(200)
                .build();

        vaccine2.addHospital(hospital);

        hospitalRepository.save(hospital);
    }

    @Test
    void 병원_조회() {
        Hospital hospital = hospitalRepository.findByHospitalName("좋은병원").get();
        String hospitalName = hospital.getHospitalName();
        System.out.println("hospitalName = " + hospitalName);
        System.out.println("vaccineName = " + hospital.getVaccines().get(0).getVaccineName());
    }
}
