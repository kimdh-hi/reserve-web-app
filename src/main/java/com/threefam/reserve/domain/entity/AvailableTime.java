package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "available_time")
@Getter
public class AvailableTime {

    @Id
    @GeneratedValue
    @Column(name = "available_time_id")
    private Long id;

    @Column(nullable = false)
    private int time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "available_date_id")
    private AvailableDate availableDate;

    // 한 타임동안 수용 가능한 인원
    private Integer acceptCount;

    @Type(type = "yes_no")
    private Boolean enabled = true;

    // 양방향 연관관계 편의 메서드
    private void addAvailableDate(AvailableDate availableDate) {
        this.availableDate = availableDate;
        availableDate.getAvailableTimes().add(this);
    }

    private void setAvailableTime(int time) {
        this.time = time;
    }

    @Builder(builderMethodName = "createAvailableTime")
    public AvailableTime(int time, Integer acceptCount) {
        this.time = time;
        this.acceptCount = acceptCount;
    }
}
