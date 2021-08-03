package com.threefam.reserve.domain;

import com.threefam.reserve.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class UserReserveTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private ReserveItemRepository reserveItemRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Rollback(false)
    void 어드민_예약_등록() {
        // 어드민 유저 등록
        User user = User.createUser()
                .email("admin@naver.com")
                .password("1234")
                .gender(Gender.MALE)
                .age(26)
                .address("경기도")
                .detailAddress("상세주소")
                .role(Role.ROLE_ADMIN)
                .build();
        User savedUser = userRepository.save(user);

        // USER의 권한이 ADMIN인 경우 어드민 테이블을 생성
        Admin admin = Admin.createAdmin()
                .userId(savedUser.getId())
                .build();
        Admin savedAdmin = adminRepository.save(admin);

        // 어드민이 예약을 생성할 때
        Reserve reserve = Reserve.createReserve()
                .reserveName("1차접종")
                .admin(savedAdmin)
                .build();
        Reserve savedReserve = reserveRepository.save(reserve);

        // 병원 생성
        Hospital hospital = Hospital.createHospital()
                .hospitalName("중앙병원")
                .availableTimes(List.of("12:00", "13:00", "14:00")) // 예약가능 시간
                .reserve(savedReserve)
                .build();
        Hospital savedHospital = hospitalRepository.save(hospital);

        // 백신 생성
        Vaccine vaccine = Vaccine.creataeVaccine()
                .vaccineName("화이자")
                .quantity(100)
                .hospital(hospital)
                .build();
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
    }

    @Test
    @Rollback(false)
    void 일반유저_예약() {
        User user = User.createUser()
                .email("test@naver.com")
                .password("1234")
                .gender(Gender.MALE)
                .age(26)
                .address("경기도")
                .detailAddress("상세주소")
                .role(Role.ROLE_USER)
                .build();
        User savedUser = userRepository.save(user);

        //
    }
}
