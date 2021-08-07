package com.threefam.reserve.repository;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import com.threefam.reserve.service.date.HolidayService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@SpringBootTest
public class HospitalRepositoryTest {

    @Autowired private HospitalRepository hospitalRepository;


    /**
     * Admin이 등록
     * 병원이름, 주소, 상세주소, 예약가능날짜(시작-종료), 일일 예약가능인원, 예약가능시간(시작-종료), 시간당 예약가능인원, 백신이름&수량 입력
     *
     * 예약가능날짜는 공휴일을 제외하고 List<String> 타입으로 변환
     * 예약가능시간은 시작-종료 시간을 1시간 단위로 나누어서 List<String> 타입으로 변환
     *    예약가능시간은 hh ~ hh로 넘어오게 하고 1시간 단위로 나누어 저장
     *
     */

    @Test
    @DisplayName("모든 병원의 이름,주소 조회 쿼리 테스트")
    void 병원이름_주소_조회(){
        List<HospitalSimpleInfoDto> list = hospitalRepository.findAllHospitalNameAndAddress();
        for (HospitalSimpleInfoDto dto : list) {
            System.out.println("dto.getHospitalName() = " + dto.getHospitalName());
            System.out.println("dto.getAddress() = " + dto.getAddress());
        }

        // 3개 등록 하나는 enabled=false
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("병원 이름으로 조회 테스트")
    void 병원이름_조회() {
        Hospital hospital = hospitalRepository.findByHospitalName("좋은병원")
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("병원이름없음");
                });
        String hospitalName = hospital.getHospitalName();
        List<Vaccine> vaccines = hospital.getVaccines();
        System.out.println("vaccines.size() = " + vaccines.size());
    }
}
