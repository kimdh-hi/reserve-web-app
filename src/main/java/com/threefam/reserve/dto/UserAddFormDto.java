package com.threefam.reserve.dto;

import com.threefam.reserve.domain.value.Gender;
import com.threefam.reserve.domain.value.Role;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 회원 가입에 사용할 폼
 */
@Data
public class UserAddFormDto {

    @NotNull(message = "이메일을 입력 해 주세요")
    private String email;

    private String password;

    @NotEmpty(message = "이름을 입력 해 주세요")
    private String name;

    @NotEmpty(message = "성별을 입력 해 주세요")
    private String gender;

    @NotEmpty(message = "나이를 입력 해 주세요")
    private Integer age;

    @NotEmpty(message = "주소를 입력 해 주세요")
    private String address;

    @NotEmpty(message = "상세 주소를 입력 해 주세요")
    private String detailAddress;

}
