package com.threefam.reserve.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @ElementCollection
    @CollectionTable(name = "available_times")
    private List<String> availableTimes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "available_dates")
    private List<String> availableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    // true: y, false: n
    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.PERSIST)
    private List<Vaccine> vaccines = new ArrayList<>();

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // 연관관계 편의 메서드
    private void addAdmin(Admin admin) {
        this.admin = admin;
        admin.getHospitals().add(this);
    }




    @Builder(builderMethodName = "createHospital")
    public Hospital(String hospitalName, List<String> availableTimes,
                    List<String> availableDates, String address, String detailAddress) {
        this.hospitalName = hospitalName;
        this.availableTimes = availableTimes;
        this.availableDates = availableDates;
        this.address = address;
        this.detailAddress = detailAddress;
        this.createAt = LocalDateTime.now();
    }
}
