package com.threefam.reserve.repository.custom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HospitalCustomRepository {

    private final EntityManager em;
}
