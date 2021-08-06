package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
public class HospitalRepositoryTest {

    @Autowired private HospitalRepository hospitalRepository;

    @BeforeEach
    @Rollback(false)
    void init() {
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

        Hospital hospital2 = Hospital.createHospital()
                .hospitalName("goodHOspital")
                .address("경기도 부천시")
                .detailAddress("good빌딩")
                .availableDates(List.of("2021/1/3", "2021/1/5", "2021/1/6"))
                .availableTimes(List.of("10:00", "13:00", "14:00","16:00"))
                .build();
        Vaccine vaccine3 = Vaccine.createVaccine()
                .vaccineName("AZ")
                .quantity(500)
                .build();
        vaccine3.addHospital(hospital2);
        Vaccine vaccine4 = Vaccine.createVaccine()
                .vaccineName("모더나")
                .quantity(200)
                .build();
        vaccine4.addHospital(hospital2);

        hospitalRepository.save(hospital2);

        Hospital hospital3 = Hospital.createHospital()
                .hospitalName("goodHOspital")
                .address("경기도 수원시")
                .detailAddress("nice빌딩")
                .availableDates(List.of("2021/1/3", "2021/1/5", "2021/1/6"))
                .availableTimes(List.of("10:00", "13:00", "14:00","16:00"))
                .build();
        Vaccine vaccine5 = Vaccine.createVaccine()
                .vaccineName("AZ")
                .quantity(500)
                .build();
        vaccine5.addHospital(hospital3);
        Vaccine vaccine6 = Vaccine.createVaccine()
                .vaccineName("모더나")
                .quantity(200)
                .build();
        vaccine6.addHospital(hospital3);

        hospital3.setEnabled(false);

        hospitalRepository.save(hospital3);
    }

    @Test
    @DisplayName("모든 병원의 이름,주소 조회 쿼리 테스트")
    void 병원이름_주소_조회(){
        List<HospitalSimpleInfoDto> list = hospitalRepository.findAllHospitalNameAndAddress();
        for (HospitalSimpleInfoDto dto : list) {
            System.out.println("dto.getHospitalName() = " + dto.getHospitalName());
            System.out.println("dto.getAddress() = " + dto.getAddress());
        }

        // 3개 등록 하나는 enabled=false
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("병원 이름으로 조회 테스트")
    void 병원이름_조회() {
        Hospital hospital = hospitalRepository.findByHospitalName("좋은병원")
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("병원이름없음");
                });
        String hospitalName = hospital.getHospitalName();
        List<Vaccine> vaccines = hospital.getVaccines();
        System.out.println("vaccines.size() = " + vaccines.size());
    }
}
