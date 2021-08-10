package com.threefam.reserve.service.admin;

import com.threefam.reserve.domain.entity.Admin;
import com.threefam.reserve.domain.entity.AvailableTime;
import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.dto.hospital.HospitalRequestDto;
import com.threefam.reserve.dto.hospital.HospitalResponseDto;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import com.threefam.reserve.repository.AdminRepository;
import com.threefam.reserve.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final HospitalRepository hospitalRepository;
    private final AdminRepository adminRepository;

    /**
     * 병원 정보 등록
     */
    @Transactional
    @Override
    public Long addHospital(HospitalRequestDto hospitalRequestDto, String adminName) {
        // 병원 엔티티 생성
        Hospital hospital = hospitalRequestDto.toHospitalEntity();
        /**
         * 현재 Authentication 객체로부터 받은 adminName을 등록하는 병원의 admin으로 설정하는 방식
         */
        Admin admin = adminRepository.findByName(adminName).get();
        hospital.setAdmin(admin);
        // 백신 엔티티 생성 및 병원 엔티티에 add
        Map<String, Integer> vaccineInfoMap = hospitalRequestDto.getVaccineInfoMap();
        for (String key : vaccineInfoMap.keySet()) {
            Vaccine vaccine = Vaccine.createVaccine()
                    .vaccineName(key)
                    .quantity(vaccineInfoMap.get(key))
                    .build();
            vaccine.addHospital(hospital);
        }
        /**
         * 예약 가능 날짜를 생성 (휴일제외)
         */
        // 예약가능시간
        List<Integer> availableTimes = getAvailableTimes(hospitalRequestDto.getStartTime(), hospitalRequestDto.getEndTime());

        // 예약가능날짜

        Hospital savedHospital = hospitalRepository.save(hospital);

        return savedHospital.getId();
    }

    /**
     * 예약가능시간 처리 메서드
     */
    private List<Integer> getAvailableTimes(String startTime, String endTime) {
        int start = Integer.parseInt(startTime);
        int end = Integer.parseInt(endTime);
        List<Integer> availableTimes = new ArrayList<>();
        for (int i=start; i<=end;i++) {
            availableTimes.add(i);
        }
        return availableTimes;
    }

    /**
     * 병원이름으로 병원 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public HospitalResponseDto getHospitalInfo(String hospitalName) {
        Hospital findHospital = hospitalRepository.findByHospitalName(hospitalName)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 병원입니다.");
                });
        List<Vaccine> vaccines = findHospital.getVaccines();
        Map<String, Integer> map = new HashMap<>();
        for (Vaccine vaccine : vaccines) {
            map.put(vaccine.getVaccineName(), vaccine.getQuantity());
        }

        // 리턴 고쳐야 함
        return null;
    }

    /**
     * 어드민이 관리하는 병원 리스트를 보여주기 위한 메서드
     */
    @Override
    public List<HospitalSimpleInfoDto> getAllSimpleHospitalInfo(String name) {
        Admin admin = adminRepository.findByName(name).get();
        return hospitalRepository.findAllByAdmin(admin);
    }
}
