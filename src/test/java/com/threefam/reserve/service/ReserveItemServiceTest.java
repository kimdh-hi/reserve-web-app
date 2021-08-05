package com.threefam.reserve.service;


import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.ReserveStatus;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.exception.vaccine.NotEnoughStockException;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveItemRepository;
import com.threefam.reserve.repository.UserRepository;
import com.threefam.reserve.service.reserveItem.ReserveItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ReserveItemServiceTest {

    @Autowired
    private ReserveItemService reserveItemService;
    @Autowired
    private ReserveItemRepository reserveItemRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;


    @Test
    void 백신_예약(){
        Hospital hospital = getHospital("좋은빌딩","서울시 강서구","좋은 빌딩",List.of("2021/1/1", "2021/1/2", "2021/1/3"),
                List.of("10:00", "13:00", "14:00") );
        Vaccine vaccine = getVaccine("화이자",100);
        Long vaccineId = saveHospitalAndVaccine(hospital, vaccine)[0];
        Long userId = saveUser("test@naver.com", "1234","뇽안", Gender.MALE, 26, "경기도 부천시", "3동",
                Role.ROLE_USER);

        em.flush();

        Long reserveId = reserveItemService.Reserve(userId, vaccineId, "2021/1/1", "10:00");

        ReserveItem reserveItem = reserveItemRepository.findById(reserveId).get();

        Assertions.assertThat(reserveItem.getStatus()).isEqualTo(ReserveStatus.COMP);
        Assertions.assertThat(reserveItem.getReserveDate()).isEqualTo("2021/1/1");
        Assertions.assertThat(vaccine.getQuantity()).isEqualTo(99);

    }

    @Test
    void 백신_재고0_false() throws Exception{
        Hospital hospital = getHospital("좋은빌딩","서울시 강서구","좋은 빌딩",List.of("2021/1/1", "2021/1/2", "2021/1/3"),
                List.of("10:00", "13:00", "14:00") );
        Vaccine vaccine = getVaccine("화이자",1);

        Long[] idList=saveHospitalAndVaccine(hospital, vaccine);

        Long userId = saveUser("test@naver.com", "1234","뇽안", Gender.MALE, 26, "경기도 부천시", "3동",
                Role.ROLE_USER);

        em.flush();

        Long reserveId = reserveItemService.Reserve(userId, idList[0], "2021/1/1", "10:00");

        em.flush();

        Hospital findHospital = hospitalRepository.findById(idList[1]).get();

        Assertions.assertThat(findHospital.getEnabled()).isEqualTo(false);

    }

    @Test
    @Rollback
    void 백신_재고_초과() throws Exception{
        Hospital hospital = getHospital("좋은빌딩","서울시 강서구","좋은 빌딩",List.of("2021/1/1", "2021/1/2", "2021/1/3"),
                List.of("10:00", "13:00", "14:00") );
        Vaccine vaccine = getVaccine("화이자",0);

        Long[] idList=saveHospitalAndVaccine(hospital, vaccine);

        Long userId = saveUser("test@naver.com", "1234","뇽안", Gender.MALE, 26, "경기도 부천시", "3동",
                Role.ROLE_USER);

        em.flush();

        org.junit.jupiter.api.Assertions.assertThrows(NotEnoughStockException.class,()->{
            reserveItemService.Reserve(userId, idList[0], "2021/1/1", "10:00");
        });

    }

    private Long saveUser(String email,String password,String name,Gender gender,int age,String address,String detailAddress,
                          Role role) {
        User user = User.createUser()
                .email(email)
                .password(password)
                .name(name)
                .gender(gender)
                .age(age)
                .address(address)
                .detailAddress(detailAddress)
                .role(role)
                .build();
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    private Long[] saveHospitalAndVaccine(Hospital hospital, Vaccine vaccine) {
        vaccine.addHospital(hospital);
        hospitalRepository.save(hospital);

        return new Long[]{vaccine.getId(),hospital.getId()};
    }

    private Vaccine getVaccine(String vaccineName,int quantity) {
        Vaccine vaccine1 = Vaccine.createVaccine()
                .vaccineName(vaccineName)
                .quantity(quantity)
                .build();
        return vaccine1;
    }

    private Hospital getHospital(String name,String address,String detailAddress,List<String> availableDates,
                                 List<String> availableTimes) {
        Hospital hospital = Hospital.createHospital()
                .hospitalName(name)
                .address(address)
                .detailAddress(detailAddress)
                .availableDates(availableDates)
                .availableTimes(availableTimes)
                .build();
        return hospital;
    }
}
