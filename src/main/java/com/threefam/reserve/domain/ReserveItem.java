package com.threefam.reserve.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    private ReserveStatus status;

    @Builder(builderMethodName = "createReserveItem")
    public ReserveItem(User user, Reserve reserve, ReserveStatus status) {
        this.user = user;
        this.reserve = reserve;
        this.status = status;
    }
}
