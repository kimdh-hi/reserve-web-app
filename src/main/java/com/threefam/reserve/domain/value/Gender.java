package com.threefam.reserve.domain.value;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Gender {

    MALE("남자"), FEMALE("여자");

    private String description;

    Gender(String description) {
        this.description = description;
    }
}
