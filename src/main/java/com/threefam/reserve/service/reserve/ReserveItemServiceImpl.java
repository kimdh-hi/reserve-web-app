package com.threefam.reserve.service.reserve;

import com.threefam.reserve.domain.entity.AvailableDate;
import com.threefam.reserve.domain.entity.AvailableTime;
import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.reserve.AvailableDateDto;
import com.threefam.reserve.dto.reserve.AvailableTimeDto;
import com.threefam.reserve.dto.vaccine.VaccineDto;
import com.threefam.reserve.dto.vaccine.VaccineReserveDto;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import com.threefam.reserve.repository.custom.ReserveItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveItemServiceImpl implements ReserveItemService{

    private final HospitalRepository hospitalRepository;
    private final HospitalCustomRepository hospitalCustomRepository;
    private final ReserveItemCustomRepository reserveItemCustomRepository;

    /**
     * 유저가 예약하기 버튼을 눌렀을 때 모든 병원의 간단한 정보 (병원이름, 주소, 백신잔여수량) 보여주기
     */
    @Override
    public List<HospitalListDto> getAllHospitalInfo(int offset, int limit) {
        return hospitalCustomRepository.findHospitalListPaging(offset, limit);
    }

    /**
     * 병원 이름으로 예약가능날짜 조회
     */
    @Override
    public List<AvailableDateDto> getAvailableDates(String hospitalName) {

        Hospital hospital = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 병원이름 입니다.");
                }
        );
        return reserveItemCustomRepository.findAvailableDatesByHospitalId(hospital.getId())
                .stream().map( m -> new AvailableDateDto(m.getId(), m.getDate())).collect(Collectors.toList());
    }

    /**
     * 예약가능시간 조회
     */
    public List<AvailableTimeDto> getAvailableTimes(Long id) {

        return reserveItemCustomRepository.findAvailableTimesByAvailableDateId(id)
                .stream().map(t -> new AvailableTimeDto(t.getId(), t.getTime())).collect(Collectors.toList());
    }

    /**
     * 예약가능백신 조회
     */
    @Override
    public List<VaccineReserveDto> getAvailableVaccineNameList(String hospitalName) {
        return reserveItemCustomRepository.findAvailableVaccines(hospitalName)
                .stream().map(v -> new VaccineReserveDto(v.getId(), v.getVaccineName())).collect(Collectors.toList());
    }
}
