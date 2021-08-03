package com.threefam.reserve.domain.value;

public enum Gender {

    MALE("남자"), FEMALE("여자");

    private String description;

    Gender(String description) {
        this.description = description;
    }
}
