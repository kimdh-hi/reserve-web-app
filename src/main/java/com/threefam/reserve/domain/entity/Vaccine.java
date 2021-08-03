package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vaccine")
@Getter
public class Vaccine extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    @Column(name = "vaccine_name")
    private String vaccineName;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Builder(builderMethodName = "createVaccine")
    public Vaccine(String vaccineName, Integer quantity, Hospital hospital) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;
        this.hospital = hospital;

        this.createAt = LocalDateTime.now();
    }

    // 연관관계 편의 메서드
    public void addHospital(Hospital hospital) {
        this.hospital = hospital;
        hospital.getVaccines().add(this);
    }
}
