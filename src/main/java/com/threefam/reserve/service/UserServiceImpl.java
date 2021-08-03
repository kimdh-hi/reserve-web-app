package com.threefam.reserve.service;

import com.threefam.reserve.domain.entity.User;
import com.threefam.reserve.dto.security.PrincipalDetails;
import com.threefam.reserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findByEmail(username);

        return findUser.stream().findFirst().map(user -> new PrincipalDetails(user)).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");
                }
        );
    }
}
