package com.threefam.reserve;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 초기 admin 데이터 저장 클래스, DI(의존 관계) 주입 후 바로 실행 될 메서드 정의
 */
@Component
@RequiredArgsConstructor
public class AdminInit {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init(){
        User admin = User.createUser()
                .email("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .name("admin")
                .age(0)
                .address("admin")
                .detailAddress("admin")
                .role(Role.ROLE_ADMIN)
                .gender(Gender.MALE)
                .build();

        userService.createUser(admin);

    }
}
