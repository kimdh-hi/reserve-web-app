package com.threefam.reserve.service;

import com.threefam.reserve.dto.reserve.ReserveItemRequestDto;
import com.threefam.reserve.repository.HospitalRepository;
import com.threefam.reserve.repository.ReserveItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReserveServiceImpl implements ReserveService{

    private final HospitalRepository hospitalRepository;
    private final ReserveItemRepository reserveItemRepository;


    @Transactional
    @Override
    public Long reserve(ReserveItemRequestDto reserveItemRequestDto) {


        return null;
    }
}
