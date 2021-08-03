package com.threefam.reserve.domain;

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
public class Admin extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "admin")
    private List<Reserve> reserves = new ArrayList<>();

    private Long userId;

    @Builder(builderMethodName = "createAdmin")
    public Admin(List<Reserve> reserves, Long userId) {
        this.reserves = reserves;
        this.userId = userId;
    }
}
