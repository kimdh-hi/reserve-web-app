package com.threefam.reserve.domain.entity;

import com.threefam.reserve.domain.value.ReserveStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id")
    private Hospital Hospital;

    private ReserveStatus status = ReserveStatus.COMP;

    @Builder(builderMethodName = "createReserveItem")
    public ReserveItem(User user, Hospital Hospital, ReserveStatus status) {
        this.user = user;
        this.Hospital = Hospital;
        this.status = status;

        this.createAt = LocalDateTime.now();
    }
}
