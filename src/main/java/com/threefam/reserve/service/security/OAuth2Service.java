package com.threefam.reserve.service.security;

import com.threefam.reserve.domain.Role;
import com.threefam.reserve.domain.User;
import com.threefam.reserve.dto.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User=super.loadUser(userRequest);

        User buildUser = User.createUser()
                .email(oAuth2User.getAttribute("email"))
                .password(bCryptPasswordEncoder.encode("threeFam"))
                .role(Role.ROLE_USER)
                .build();
        return new PrincipalDetails(buildUser,oAuth2User.getAttributes());
    }
}
