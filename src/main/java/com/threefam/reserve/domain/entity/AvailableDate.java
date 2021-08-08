package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "available_date")
@Getter
public class AvailableDate {

    @Id
    @GeneratedValue
    @Column(name = "available_data_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(nullable = false)
    private String date;

    // 양방향
    @OneToMany(mappedBy = "availableDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTime> availableTimes = new ArrayList<>();

    // 일일 수용 가능 인원
    @Column(name = "accept_count")
    private Integer acceptCount;

    @Type(type = "yes_no")
    private Boolean enabled = true;

    // 양방향 연관관계 편의 메서드
    private void addHospital(Hospital hospital) {
        this.hospital = hospital;
        hospital.getAvailableDates().add(this);
    }
}
