package com.threefam.reserve.domain;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @Column(name = "vaccine_name")
    private String vaccineName;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Builder(builderMethodName = "creataeVaccine")
    public Vaccine(String vaccineName, Integer quantity, Hospital hospital) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;
        this.hospital = hospital;
        this.createAt = LocalDateTime.now();
    }
}
