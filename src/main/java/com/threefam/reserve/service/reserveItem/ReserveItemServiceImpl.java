package com.threefam.reserve.service.reserveItem;

import com.threefam.reserve.domain.entity.Hospital;
import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.domain.value.ReserveStatus;
import com.threefam.reserve.dto.hospital.HospitalListDto;
import com.threefam.reserve.dto.hospital.HospitalSimpleInfoDto;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveItemRepository;
import com.threefam.reserve.repository.UserRepository;
import com.threefam.reserve.repository.VaccineRepository;
import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveItemServiceImpl implements ReserveItemService{

    private final HospitalCustomRepository hospitalCustomRepository;
    private final ModelMapper mapper;

    /**
     * 유저가 예약하기 버튼을 눌렀을 때 모든 병원의 간단한 정보 (병원이름, 주소, 백신잔여수량) 보여주기
     */
    @Override
    public List<HospitalListDto> getAllHospitalInfo(int offset, int limit) {
        return hospitalCustomRepository.findHospitalListPaging(offset, limit);
    }

    @Override
    public void getReserveHospitalInfo(String hospitalName) {

    }
}
