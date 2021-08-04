package com.threefam.reserve.service;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 회원가입(){
        User user = User.createUser()
                .email("test@naver.com")
                .password("1234")
                .name("나이스")
                .gender(Gender.MALE)
                .age(26)
                .address("경기도 부천시")
                .detailAddress("좋은아파트 105-1304")
                .role(Role.ROLE_USER)
                .build();

        User saveUser = userService.createUser(user);

        Assertions.assertEquals(saveUser,userRepository.findByEmail(saveUser.getEmail()).get());
    }

    @Test
    void 이메일로엔티티찾기(){
        User user = User.createUser()
                .email("test@naver.com")
                .password("1234")
                .name("나이스")
                .gender(Gender.MALE)
                .age(26)
                .address("경기도 부천시")
                .detailAddress("좋은아파트 105-1304")
                .role(Role.ROLE_USER)
                .build();
        User saveUser = userService.createUser(user);

        Assertions.assertEquals(userService.getUserByEmail(saveUser.getEmail()),userRepository.findByEmail(saveUser.getEmail()).get());
        Assertions.assertEquals(userService.getUserByEmail("null"),null);

        org.assertj.core.api.Assertions.assertThat(saveUser.getEmail()).isEqualTo("test@naver.com");
    }


}
