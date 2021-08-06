package com.threefam.reserve.request;

import com.threefam.reserve.domain.value.Gender;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 일반 사용자가 Oauth2 인증을 마치고 추가로 입력받는 폼에 대한 입력객체
 * Oauth를 통해 받을 수 있는 정보는 disable 처리하도록 해야함
 */
@Data
public class RegisterFormRequest {
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;
    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;
    @NotNull(message = "나이를 입력해주세요.")
    private Integer age;
    @NotNull(message = "주소를 입력해주세요.")
    private String address;
    @NotNull(message = "상세주소를 입력해주세요.")
    private String detailAddress;
}

