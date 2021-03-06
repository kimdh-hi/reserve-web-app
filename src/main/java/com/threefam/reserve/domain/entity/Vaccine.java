package com.threefam.reserve.domain.entity;

import com.threefam.reserve.exception.vaccine.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(nullable = false)
    private Integer quantity;

    public void cancel() {
        this.quantity++;
        this.enabled=true;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Type(type="yes_no")
    private boolean enabled = true;

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    @Builder(builderMethodName = "createVaccine")
    public Vaccine(String vaccineName, Integer quantity) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;

        this.createAt = LocalDateTime.now();
    }
    // 연관관계 편의 메서드
    public void addHospital(Hospital hospital) {
        this.hospital = hospital;
        hospital.getVaccines().add(this);
    }

    //==비즈니스 로직==//

    //예약 취소 시, 사용
    public void addStock(){
        this.quantity+=1;
    }

    //예약 시, 사용
    public void removeStock(){
        int restStock=this.quantity-1;
        if(restStock==0){
            setEnabled(false);
        }
        if(restStock<0){
            throw new NotEnoughStockException("예약 가능한 수량이 부족합니다.");
        }

        this.quantity=restStock;
    }

    //병원 수정 시, 사용
    public void updateVaccineQty(Integer quantity){
        this.quantity=quantity;
    }
}
