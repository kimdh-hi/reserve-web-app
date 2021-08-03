package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hospital")
@Getter
public class Hospital extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    @Column(name = "hospital_name")
    private String hospitalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    @ElementCollection
    @CollectionTable(name = "available_times")
    private List<String> availableTimes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "available_dates")
    private List<String> availableDates = new ArrayList<>();

    private String address;

    private String detailAddress;


    @Builder(builderMethodName = "createHospital")
    public Hospital(String hospitalName, Reserve reserve, List<String> availableTimes, List<String> availableDates, String address, String detailAddress) {
        this.hospitalName = hospitalName;
        this.reserve = reserve;
        this.availableTimes = availableTimes;
        this.availableDates = availableDates;
        this.address = address;
        this.detailAddress = detailAddress;

        this.createAt = LocalDateTime.now();
    }
}
