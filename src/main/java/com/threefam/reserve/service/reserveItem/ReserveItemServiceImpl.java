package com.threefam.reserve.service.reserveItem;

import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.domain.value.ReserveStatus;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveItemRepository;
import com.threefam.reserve.repository.UserRepository;
import com.threefam.reserve.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveItemServiceImpl implements ReserveItemService{

    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    @Override
    public void getReserveHospitalInfo(String hospitalName) {
        // 예역하고자 하는 병원 조회
        hospitalRepository.findByHospitalName(hospitalName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 병원입니다.");
                }
        );

    }
}
