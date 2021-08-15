package com.threefam.reserve.service.admin;

import com.threefam.reserve.domain.entity.*;


import com.threefam.reserve.dto.hospital.*;
import com.threefam.reserve.dto.reserve.ReserveItemWithUsernameDto;
import com.threefam.reserve.repository.AdminRepository;
import com.threefam.reserve.repository.AvailableDateRepository;
import com.threefam.reserve.repository.AvailableTimeRepository;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import com.threefam.reserve.repository.custom.ReserveItemCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.threefam.reserve.service.Holiday;
@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final HospitalCustomRepository hospitalCustomRepository;
    private final HospitalRepository hospitalRepository;
    private final AdminRepository adminRepository;
    private final Holiday holiday;
    private final AvailableTimeRepository availableTimeRepository;
    private final AvailableDateRepository availableDateRepository;
    private final ReserveItemCustomRepository reserveItemCustomRepository;

    /**
     * 병원 정보 등록
     */
    @Transactional
    @Override
    public Long addHospital(HospitalRequestDto hospitalRequestDto,String adminName) throws Exception{

        // 병원 엔티티 생성
        Hospital hospital = hospitalRequestDto.toHospitalEntity();
        /**
         * 현재 Authentication 객체로부터 받은 adminName을 등록하는 병원의 admin으로 설정하는 방식
         */
        Admin admin = adminRepository.findByName(adminName).get();
        hospital.setAdmin(admin);
        // 총 백신 수량 (종류 상관X)
        Integer total = 0;
        // 백신 엔티티 생성 및 병원 엔티티에 add
        Map<String, Integer> vaccineInfoMap = hospitalRequestDto.getVaccineInfoMap();
        for (String key : vaccineInfoMap.keySet()) {
            Vaccine vaccine = Vaccine.createVaccine()
                    .vaccineName(key)
                    .quantity(vaccineInfoMap.get(key))
                    .build();
            vaccine.addHospital(hospital);
            total += vaccineInfoMap.get(key);
        }
        hospital.setTotalVaccineQuantity(total);

        /**
         * 예약 가능 날짜를 생성 (휴일제외)
         */
        // 예약가능시간
        List<Integer> availableTimeList = getAvailableTimes(hospitalRequestDto.getStartTime(), hospitalRequestDto.getEndTime());

        // 예약가능날짜
        List<String> holidays = holiday.holidayList(hospitalRequestDto.getStartDate(), hospitalRequestDto.getEndDate());
        List<String> availableDateList = holiday.availableDateList(hospitalRequestDto.getStartDate(), hospitalRequestDto.getEndDate(), holidays);

        for (String date : availableDateList) {
            AvailableDate availableDate= AvailableDate.createAvailableDate()
                    .date(date)
                    .acceptCount(hospitalRequestDto.getDateAccept())
                    .build();
            for (Integer time : availableTimeList) {
                AvailableTime availableTime= AvailableTime.createAvailableTime()
                        .time(time)
                        .acceptCount(hospitalRequestDto.getTimeAccept())
                        .build();
                availableTime.addAvailableDate(availableDate);
            }
            availableDate.addHospital(hospital);
        }


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

    @Override
    public List<HospitalListDto> getHospitalList(String name) {
        Admin admin = adminRepository.findByName(name).get();
        return hospitalCustomRepository.findAllHospitalInfo(admin.getId());
    }

    /**
     * 병원 상세 정보 조회 후 dto로 변환
     */
    @Override
    public HospitalUpdateDto getHospital(Long id) {
        Optional<Hospital> hospitalDetail = hospitalCustomRepository.findHospitalDetail(id);
        Hospital hospital = hospitalDetail.stream().findFirst().orElse(null);

        List<AvailableDate> availableDates = hospital.getAvailableDates();
        List<AvailableTime> availableTimes = availableDates.get(0).getAvailableTimes();
        List<Vaccine> vaccines = hospital.getVaccines();

        Map<String,Integer> vaccineMap=new HashMap<>();

        for (Vaccine vaccine : vaccines) {
            vaccineMap.put(vaccine.getVaccineName(),vaccine.getQuantity());
        }

        return HospitalUpdateDto.createHospitalUpdateDto()
                .id(hospital.getId())
                .hospitalName(hospital.getHospitalName())
                .address(hospital.getAddress())
                .detailAddress(hospital.getDetailAddress())
                .dateAccept(hospital.getDateAccept())
                .timeAccept(hospital.getTimeAccept())
                .startDate(availableDates.get(0).getDate())
                .endDate(availableDates.get(availableDates.size()-1).getDate())
                .startTime(String.valueOf(availableTimes.get(0).getTime()))
                .endTime(String.valueOf(availableTimes.get(availableTimes.size()-1).getTime()))
                .astrazeneka(vaccineIsPresent(vaccineMap,"아스트라제네카"))
                .fizar(vaccineIsPresent(vaccineMap,"화이자"))
                .janssen(vaccineIsPresent(vaccineMap,"얀센"))
                .modena(vaccineIsPresent(vaccineMap,"모더나"))
                .build();
    }

    @Override
    @Transactional
    public Long hospitalUpdate(HospitalUpdateDto dto) throws ParseException {
        Optional<Hospital> hospitalDetail = hospitalCustomRepository.findHospitalDetail(dto.getId());
        Hospital hospital = hospitalDetail.stream().findFirst().orElse(null);

        //수정 목록
        List<Vaccine> vaccines = hospital.getVaccines();

        //==백신 수정==//
        Map<String, Integer> vaccineInfoMap = dto.getVaccineInfoMap();

        Integer total = 0;

        //백신 이름 리스트. 추가된 백신, 수량이 0이된 백신 확인 위해
        List<String> vaccineNames=new ArrayList<>();
        for (Vaccine vaccine : vaccines) {
            vaccineNames.add(vaccine.getVaccineName());
        }

        for(String key:vaccineInfoMap.keySet()){
            total+=vaccineInfoMap.get(key);
            //추가된 백신이 있는 지 확인
            if(!vaccineNames.contains(key)){
                Vaccine aditionalVaccine = Vaccine.createVaccine()
                        .vaccineName(key)
                        .quantity(vaccineInfoMap.get(key))
                        .build();
                aditionalVaccine.addHospital(hospital);
            }
            // 기존의 백신에서 수량이 바뀌었는지 확인
            else {
                for (Vaccine vaccine : vaccines) {
                    if (vaccine.getVaccineName().equals(key)) {
                        //수량 수정 시, 0을 입력하면 dto로 전달이 안되기 때문에 확인을 위한 과정
                        vaccineNames.remove(key);
                        //이미 있는 백신이라면 수량이 같으면 update 필요 x 수량이 다르면 update
                        if (vaccine.getQuantity() != vaccineInfoMap.get(key)) {
                            vaccine.updateVaccineQty(vaccineInfoMap.get(key));
                            vaccine.setEnabled(true);
                        }
                        break;
                    }
                }
            }
        }
        // 비어있지 않다면, 수정 폼에서 0으로 설정되었다는 뜻. 수량을 0으로 설정하자
        if(!vaccineNames.isEmpty()){
            for (String vaccineName : vaccineNames) {
                Vaccine vaccine = vaccines.stream().filter(v -> v.getVaccineName().equals(vaccineName)).findFirst().orElse(null);
                if(vaccine!=null){
                    vaccine.updateVaccineQty(0);
                    vaccine.setEnabled(false);
                }
            }
        }

        //총 수량의 합이 같다면 update x
        if(total!=hospital.getTotalQuantity()) {
            //원래 0이었다면 false 였으니
            if(hospital.getTotalQuantity()==0)
                hospital.setEnabled(true);

            hospital.setTotalVaccineQuantity(total);

            if(hospital.getTotalQuantity()==0)
                hospital.setEnabled(false);
        }

        //병원의 예약가능 날짜 리스트
        List<AvailableDate> availableDates = hospital.getAvailableDates();

        //==dateAccept수정부분==//
        Integer dateAcceptCount = dto.getDateAccept();
        Integer originDateAccept = hospital.getDateAccept();
        //dateAccept가 수정되었다면
        if(originDateAccept != dateAcceptCount){
            hospital.updateDateAccept(dateAcceptCount);
            int updateDateAcceptCount = dateAcceptCount - originDateAccept;

            List<Long> availableDateIds=new ArrayList<>();

            //수정된 dateAccept 적용 시, 0보다 작거나 같아질 경우
            boolean flag=false;
            for (AvailableDate availableDate : availableDates) {
                if(availableDate.getAcceptCount()+updateDateAcceptCount<=0){
                    availableDateIds.add(availableDate.getId());
                    flag=true;
                }
            }
            availableDateRepository.updateAvailableDateAcceptCount(updateDateAcceptCount
                    ,hospital.getId());
            if(flag)
            {
                availableDateRepository.updateAvailableDateAcceptCountToZero(availableDateIds);
            }
        }

        //==timeAccept수정부분==//
        Integer timeAcceptCount = dto.getTimeAccept();
        Integer originTimeAccept = hospital.getTimeAccept();

        //timeAccept가 수정되었다면
        if(originTimeAccept !=timeAcceptCount){
            int updateAcceptCount = timeAcceptCount - originTimeAccept;

            hospital.updateTimeAccept(timeAcceptCount);

            List<Long> availableDateIds=new ArrayList<>();
            List<Long> availableTimeIds=new ArrayList<>();

            boolean flag=false;
            for (AvailableDate availableDate : availableDates) {
                availableDateIds.add(availableDate.getId());
                List<AvailableTime> availableTimes = availableDate.getAvailableTimes();

                //수량이 0보다 작거나 같아지는 것이 있으면
                for (AvailableTime availableTime : availableTimes) {
                    if(availableTime.getAcceptCount()+updateAcceptCount<=0){
                        availableTimeIds.add(availableTime.getId());
                        flag=true;
                    }
                }
            }
            availableTimeRepository.updateAvailableTimeAcceptCount(updateAcceptCount
                    ,availableDateIds);
            if(flag){
                availableTimeRepository.updateAvailableTimeAcceptCountToZero(availableTimeIds);
            }
        }

        return hospital.getId();
    }

    /**
     * 예약 현황 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public List<ReserveItemWithUsernameDto> getReserveItemCondition() {
        List<ReserveItem> reserveItems = reserveItemCustomRepository.findAllReserveItem();
        if(reserveItems.isEmpty()) {
            return null;
        }

        return reserveItems.stream()
                .map(ri->new ReserveItemWithUsernameDto(ri))
                .collect(Collectors.toList());
    }



    /**
     * 병원 정보 조회 시 , 해당 백신이 존재하는 지에 대한 여부
     */
    private Integer vaccineIsPresent(Map<String, Integer> vaccineMap,String key){
        Integer vaccineQty = vaccineMap.get(key);

        if(vaccineQty ==null)
            return 0;
        return vaccineQty;
    }
}