package com.threefam.reserve;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * web application 실행 시, admin이 등록되어 있는지 테스의
 */

@SpringBootTest
@Transactional
public class AdminInitTest {

    @Autowired
    private UserService userService;

    @Test
    void admin_등록(){
        User admin = userService.getUserByEmail("admin");

        Assertions.assertThat(admin.getEmail()).isEqualTo("admin");
        Assertions.assertThat(admin.getRole()).isEqualTo(Role.ROLE_ADMIN);
    }
}
