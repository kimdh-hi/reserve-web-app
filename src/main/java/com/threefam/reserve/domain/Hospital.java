package com.threefam.reserve.domain;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(name = "hospital_name")
    private String hospitalName;

    @ManyToOne
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    @ElementCollection
    @CollectionTable(name = "avaliable_times")
    private List<String> availableTimes = new ArrayList<>();

    @Builder(builderMethodName = "createHospital")
    public Hospital(String hospitalName, Reserve reserve, List<String> availableTimes) {
        this.hospitalName = hospitalName;
        this.reserve = reserve;
        this.availableTimes = availableTimes;
        this.createAt = LocalDateTime.now();
    }
}
