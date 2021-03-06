package com.threefam.reserve.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String,Object> idAttributes;
    private final Map<String,Object> normalAttributes;


    @Override
    public String getEmail() {
        if((String)normalAttributes.get("email")==null){
            return idAttributes.get("id")+"@kakao.com";
        }
        return (String)normalAttributes.get("email");
    }

    @Override
    public String getName() {
        return (String)((Map)normalAttributes.get("profile")).get("nickname");
    }
}
