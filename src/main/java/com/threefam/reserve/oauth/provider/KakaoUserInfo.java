package com.threefam.reserve.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String,Object> attributes;

    @Override
    public String getEmail() {
        if((String)attributes.get("email")==null){
            String email = UUID.randomUUID().toString();
            return email+"@kakao.com";
        }
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)((Map)attributes.get("profile")).get("nickname");
    }
}
