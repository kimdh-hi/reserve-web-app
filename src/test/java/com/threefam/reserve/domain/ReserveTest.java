package com.threefam.reserve.domain;

import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveRepository;
import com.threefam.reserve.repository.VaccineRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ReserveTest {

    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private VaccineRepository vaccineRepository;

//    @AfterEach
//    void clear() {
//        reserveRepository.deleteAll();
//        hospitalRepository.deleteAll();
//    }

    @Test
    @Rollback(false)
    void createTest() {
        Reserve reserve = Reserve.createReserve()
                .reserveName("백신접종")
                .build();

        Reserve savedReserve = reserveRepository.save(reserve);

        Assertions.assertThat(savedReserve.getReserveName()).isEqualTo("백신접종");
    }

    @Test
    @Rollback(false)
    void createReserveWithHospitalAndVaccine() {

        List<String> times = List.of("12:00", "13:00", "14:00");

        Reserve reserve = Reserve.createReserve()
                .reserveName("1차접종")
                .build();

        Reserve savedReserve = reserveRepository.save(reserve);

        Hospital hospital = Hospital.createHospital()
                .hospitalName("중앙병원")
                .availableTimes(times)
                .reserve(savedReserve)
                .build();

        Hospital savedHospital = hospitalRepository.save(hospital);

        Vaccine vaccine = Vaccine.creataeVaccine()
                .vaccineName("화이자")
                .quantity(100)
                .hospital(savedHospital)
                .build();

        Vaccine savedVaccine = vaccineRepository.save(vaccine);

        Assertions.assertThat(savedHospital.getHospitalName()).isEqualTo("중앙병원");
        Assertions.assertThat(savedHospital.getReserve().getReserveName()).isEqualTo(savedReserve.getReserveName());
        Assertions.assertThat(savedVaccine.getVaccineName()).isEqualTo("화이자");
        Assertions.assertThat(savedVaccine.getHospital().getReserve().getReserveName()).isEqualTo("1차접종");
    }
}
