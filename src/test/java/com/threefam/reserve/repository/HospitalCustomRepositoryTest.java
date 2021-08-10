package com.threefam.reserve.repository;

import com.threefam.reserve.repository.custom.HospitalCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class HospitalCustomRepositoryTest {

    @Autowired
    private HospitalCustomRepository hospitalCustomRepository;
}
