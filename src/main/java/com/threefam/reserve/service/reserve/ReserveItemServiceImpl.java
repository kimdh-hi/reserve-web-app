package com.threefam.reserve.service.reserve;

import com.threefam.reserve.domain.entity.*;
import com.threefam.reserve.domain.value.ReserveStatus;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.reserve.AvailableDateDto;
import com.threefam.reserve.dto.reserve.AvailableTimeDto;
import com.threefam.reserve.dto.reserve.ReserveItemSimpleDto;
import com.threefam.reserve.dto.vaccine.VaccineReserveDto;
import com.threefam.reserve.repository.AvailableDateRepository;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveItemRepository;
import com.threefam.reserve.repository.UserRepository;
import com.threefam.reserve.repository.custom.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReserveItemServiceImpl implements ReserveItemService{

    private final VaccineCustomRepository vaccineCustomRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalCustomRepository hospitalCustomRepository;
    private final ReserveItemCustomRepository reserveItemCustomRepository;
    private final AvailableTimeCustomRepository availableTimeCustomRepository;
    private final AvailableDateRepository availableDateRepository;
    private final UserRepository userRepository;
    private final ReserveItemRepository reserveItemRepository;
    private final AvailableDateCustomRepository availableDateCustomRepository;


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
    public List<AvailableDateDto> getAvailableDates(Long HospitalId) {

        return reserveItemCustomRepository.findAvailableDatesByHospitalId(HospitalId)
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
    public List<VaccineReserveDto> getAvailableVaccineNameList(Long hospitalId) {
        return reserveItemCustomRepository.findAvailableVaccines(hospitalId)
                .stream().map(v -> new VaccineReserveDto(v.getId(), v.getVaccineName())).collect(Collectors.toList());
    }

    /**
     * 예약처리
     */
    @Override
    public Long reserve(String username, Long hospitalId, String vaccineName, Long dateId, Long timeId) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 병원입니다.");
                }
        );
        Vaccine vaccine = vaccineCustomRepository.findVaccine(hospitalId, vaccineName);
        AvailableTime time = availableTimeCustomRepository.findAvailableTimeById(timeId);

        time.decreaseCount();
        if (time.getAcceptCount() <= 0) time.setEnabled(false);

        hospital.removeStock();

        vaccine.removeStock();

        AvailableDate availableDate = availableDateRepository.findById(dateId).get();

        User user = userRepository.findByEmail(username).get();

        ReserveItem reserveItem = ReserveItem.createReserveItem()
                .Hospital(hospital)
                .reserveDate(availableDate.getDate())
                .reserveTime(time.getTime())
                .status(ReserveStatus.COMP)
                .user(user)
                .vaccineName(vaccineName)
                .build();
        ReserveItem savedReserveItem = reserveItemRepository.save(reserveItem);

        return user.getId();
    }

    /**
     * 예약서 조회
     */
    @Override
    public ReserveItemSimpleDto getReserveResult(String username) {
        log.info("getReserveResult username = {}", username);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );
        return reserveItemRepository.findByUserId(user.getId()).orElseGet(
                () -> { return new ReserveItemSimpleDto(); });
    }

    /**
     * 이미 예약한 회원인지 확인.
     */
    @Override
    public void validateDuplicateUser(String username){
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );
        Optional<ReserveItemSimpleDto> reserveItemByUserId = reserveItemRepository.findByUserId(user.getId());
        if(!reserveItemByUserId.isEmpty()){
            throw new IllegalStateException("이미 예약한 회원 입니다.");
        }

    }

    /**
     * 예약취소
     */
    @Override
    public void cancelReserveItem(Long reserveItemId) {
        ReserveItem reserveItem = reserveItemRepository.findById(reserveItemId).get();

        Hospital hospital = reserveItem.getHospital();
        hospital.cancel();

        Vaccine vaccine = vaccineCustomRepository.findVaccineDisabled(hospital.getId(), reserveItem.getVaccineName());
        vaccine.cancel();

        AvailableDate date = availableDateCustomRepository.findAvailableDateByHospitalIdAndDate(hospital.getId(), reserveItem.getReserveDate());
        AvailableTime time = availableTimeCustomRepository.findAvailableTimeByTimeAndDateId(reserveItem.getReserveTime(), date.getId());
        time.cancel();

        reserveItemRepository.deleteById(reserveItemId);
    }
}
