package com.threefam.reserve.domain.value;

public enum ReserveStatus {

    COMP("예약완료"), CANCEL("예약취소");

    private String description;

    ReserveStatus(String description) {
        this.description = description;
    }
}
