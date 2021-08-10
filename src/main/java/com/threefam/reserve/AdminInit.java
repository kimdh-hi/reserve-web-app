package com.threefam.reserve;

import com.threefam.reserve.domain.entity.*;
import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.dto.vaccine.VaccineDto;
import com.threefam.reserve.repository.AdminRepository;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 초기 admin 데이터 저장 클래스, DI(의존 관계) 주입 후 바로 실행 될 메서드 정의
 */
@Component
@RequiredArgsConstructor
public class AdminInit {

    private final UserService userService;
    private final AdminRepository adminRepository;
    private final HospitalRepository hospitalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init(){
        User user = User.createUser()
                .email("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .name("admin")
                .age(0)
                .address("admin")
                .detailAddress("admin")
                .role(Role.ROLE_ADMIN)
                .gender(Gender.MALE)
                .build();

        User savedUser = userService.createUser(user);
        Admin admin = Admin.createAdmin()
                .name(savedUser.getName())
                .build();
        adminRepository.save(admin);

        Hospital hospital = Hospital.createHospital()
                .hospitalName("HospitalA")
                .address("서울특별시 강서구")
                .detailAddress("A빌딩")
                .build();
        hospital.setAdmin(admin);

        Vaccine astrazeneka = Vaccine.createVaccine()
                .vaccineName("아스트라제네카")
                .quantity(50)
                .build();
        Vaccine janssen = Vaccine.createVaccine()
                .vaccineName("얀센")
                .quantity(20)
                .build();
        Vaccine modena = Vaccine.createVaccine()
                .vaccineName("모더나")
                .quantity(40)
                .build();
        Vaccine fizar = Vaccine.createVaccine()
                .vaccineName("화이자")
                .quantity(10)
                .build();
        hospital.getVaccines().add(astrazeneka);
        hospital.getVaccines().add(janssen);
        hospital.getVaccines().add(modena);
        hospital.getVaccines().add(fizar);

        List<AvailableDate> availableDateList = new ArrayList<>();
        availableDateList.add(AvailableDate.createAvailableDate().date("2021/1/1").acceptCount(100).build());
        availableDateList.add(AvailableDate.createAvailableDate().date("2021/1/2").acceptCount(100).build());
        availableDateList.add(AvailableDate.createAvailableDate().date("2021/1/3").acceptCount(100).build());
        availableDateList.add(AvailableDate.createAvailableDate().date("2021/1/4").acceptCount(100).build());

        AvailableTime time1 = AvailableTime.createAvailableTime().time(9).acceptCount(10).build();
        AvailableTime time2 = AvailableTime.createAvailableTime().time(10).acceptCount(10).build();
        AvailableTime time3 = AvailableTime.createAvailableTime().time(11).acceptCount(10).build();
        AvailableTime time4 = AvailableTime.createAvailableTime().time(13).acceptCount(10).build();

        for (AvailableDate availableDate : availableDateList) {
            availableDate.getAvailableTimes().add(time1);
            availableDate.getAvailableTimes().add(time2);
            availableDate.getAvailableTimes().add(time3);
            availableDate.getAvailableTimes().add(time4);
            hospital.getAvailableDates().add(availableDate);
        }

        hospital.setTotalVaccineQuantity(120);

        hospitalRepository.save(hospital);
    }
}
