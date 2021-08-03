package com.threefam.reserve.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * USER가 예약하는 예약
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reserve")
@Getter
public class Reserve extends BaseEntity {

    @Column(name = "reserve_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reserveName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Builder(builderMethodName = "createReserve")
    public Reserve(String reserveName, Admin admin) {
        this.admin = admin;
        this.reserveName = reserveName;
        this.createAt = LocalDateTime.now();
    }

}
