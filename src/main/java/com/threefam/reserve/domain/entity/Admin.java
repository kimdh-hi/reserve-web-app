package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin")
@Getter
public class Admin extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private Long user_id;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Hospital> hospitals = new ArrayList<>();

    @Builder(builderMethodName = "createAdmin")
    public Admin(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }
}