package com.threefam.reserve.controller;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.Role;
import com.threefam.reserve.dto.user.UserAddFormDto;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     *
     * 회원 가입 form 으로 보내줄 Controller. oauth로 로그인한 사람의 계정을 보고
     * 이미 회원 가입된 User면 다시 Home으로 , 아니면 추가정보를 작성하도록
     * password는회원 가입 화면에서 hidden으로 전송 받도록 할 것
     * Role은 그냥 User로
     * 회원 가입 페이지는 joinForm으로
     *
     */
    @GetMapping("/join")
    public String joinForm(@AuthenticationPrincipal PrincipalDetails details, Model model){

        if(userService.getUserByEmail(details.getUsername())==null){
            UserAddFormDto userAddFormDto=new UserAddFormDto();
            userAddFormDto.setEmail(details.getUsername());
            userAddFormDto.setName(details.getName());
            userAddFormDto.setPassword(details.getPassword());
            return "joinForm";
        }
        return "Login";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserAddFormDto form){
        User user = User.createUser()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .role(Role.ROLE_USER)
                .gender(Gender.valueOf(form.getGender()))
                .age(form.getAge())
                .address(form.getAddress())
                .detailAddress(form.getDetailAddress())
                .build();

        userService.createUser(user);

        return "redirect:/";
    }
}
