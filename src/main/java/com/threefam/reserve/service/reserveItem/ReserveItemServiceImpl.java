package com.threefam.reserve.service.reserveItem;

import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveItemServiceImpl implements ReserveItemService{

    private final HospitalCustomRepository hospitalCustomRepository;

    /**
     * 유저가 예약하기 버튼을 눌렀을 때 모든 병원의 간단한 정보 (병원이름, 주소, 백신잔여수량) 보여주기
     */
    @Override
    public List<HospitalListDto> getAllHospitalInfo(int offset, int limit) {
        return hospitalCustomRepository.findHospitalListPaging(offset, limit);
    }
}
