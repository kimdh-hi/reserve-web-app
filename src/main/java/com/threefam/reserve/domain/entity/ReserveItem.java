package com.threefam.reserve.domain.entity;

import com.threefam.reserve.domain.value.ReserveStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * USER의 예약서
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reserve_item")
@Getter
public class ReserveItem extends BaseEntity {

    @Column(name = "reserve_item_id")
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital Hospital;

    private ReserveStatus status = ReserveStatus.COMP;

    private String reserveDate;

    private int reserveTime;

    @Builder(builderMethodName = "createReserveItem")
    public ReserveItem(User user, Hospital Hospital, ReserveStatus status, String reserveDate, int reserveTime) {
        this.user = user;
        this.Hospital = Hospital;
        this.status = status;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;

        this.createAt = LocalDateTime.now();
    }

    //==비즈니스 로직==//
    //예약 날짜 및 예약 시간 update
    public void updateDateAndTime(String reserveDate,int reserveTime){
        this.reserveDate=reserveDate;
        this.reserveTime=reserveTime;
    }

}
