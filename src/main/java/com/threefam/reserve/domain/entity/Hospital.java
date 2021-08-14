package com.threefam.reserve.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.threefam.reserve.exception.vaccine.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
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

    // 양방향
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hospital"})
    private List<AvailableDate> availableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getHospitals().add(this);
    }

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    public void cancel() {
        this.totalQuantity++;
    }

    @Column(name = "date_accept")
    private Integer dateAccept;
    @Column(name = "time_accept")
    private Integer timeAccept;

    public void setTotalVaccineQuantity(Integer qty) {
        this.totalQuantity = qty;
    }

    public void removeStock() {
        int restStock=this.totalQuantity-1;
        if(restStock==0){
            setEnabled(false);
        }
        if(restStock<0){
            throw new NotEnoughStockException("예약 가능한 수량이 부족합니다.");
        }

        this.totalQuantity=restStock;
    }

    public void updateDateAccept(Integer dateAccept){this.dateAccept=dateAccept;}

    public void updateTimeAccept(Integer timeAccept){this.timeAccept=timeAccept;}

    // true: y, false: n
    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties({"hospital"})
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
    public Hospital(String hospitalName, String address, String detailAddress,Integer dateAccept,Integer timeAccept) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.createAt = LocalDateTime.now();
        this.dateAccept=dateAccept;
        this.timeAccept=timeAccept;
    }
}
