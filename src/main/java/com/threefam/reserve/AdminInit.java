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

    //@PostConstruct
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
                .dateAccept(100)
                .timeAccept(10)
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

        astrazeneka.addHospital(hospital);
        janssen.addHospital(hospital);
        modena.addHospital(hospital);
        fizar.addHospital(hospital);

        List<String> dateList=new ArrayList<>();
        dateList.add("2021.1.1");
        dateList.add("2021.1.2");
        dateList.add("2021.1.3");
        dateList.add("2021.1.4");

        List<Integer> timeList=new ArrayList<>();
        timeList.add(9);
        timeList.add(10);
        timeList.add(11);
        timeList.add(13);

        for (String date : dateList) {
            AvailableDate availableDate= AvailableDate.createAvailableDate()
                    .date(date)
                    .acceptCount(100)
                    .build();
            for (Integer time : timeList) {
                AvailableTime availableTime = AvailableTime.createAvailableTime()
                        .time(time)
                        .acceptCount(10)
                        .build();
                availableTime.addAvailableDate(availableDate);
            }
            availableDate.addHospital(hospital);
        }
        hospital.setTotalVaccineQuantity(120);

        hospitalRepository.save(hospital);
    }
}
