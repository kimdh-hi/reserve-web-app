package com.threefam.reserve.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reserve")
@Getter
public class Reserve extends BaseEntity {

    @Column(name = "reserve_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reserve")
    private List<ReserveItem> reserveItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReserveStatus status;
}
