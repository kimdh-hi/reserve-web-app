package com.threefam.reserve.service.reserveItem;

import com.threefam.reserve.domain.entity.ReserveItem;
import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.domain.entity.Vaccine;
import com.threefam.reserve.domain.value.ReserveStatus;
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
    private final VaccineRepository vaccineRepository;
    private final ReserveItemRepository reserveItemRepository;

    @Override
    public Long Reserve(Long userId, Long vaccineId,String reserveDate,String reserveTime) {

        Optional<User> findUser = userRepository.findById(userId);
        Optional<Vaccine> findVaccine = vaccineRepository.findById(vaccineId);

        User user = findUser.stream().findFirst().orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자 입니다.");
                }
        );

        Vaccine vaccine = findVaccine.stream().findFirst().orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 백신 입니다.");
                }
        );

        vaccine.removeStock();

        ReserveItem reserveItem = ReserveItem.createReserveItem()
                .Hospital(vaccine.getHospital())
                .user(user)
                .status(ReserveStatus.COMP)
                .build();

        reserveItem.updateDateAndTime(reserveDate,reserveTime);

        ReserveItem saveReserveItem = reserveItemRepository.save(reserveItem);

        return saveReserveItem.getId();
    }
}
