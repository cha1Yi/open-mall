package com.openmall.passport.application.oauth2.extend.oidc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自定义 OIDC 用户信息服务
 *
 * @author wuxuan
 * @since 2024/7/5 17:15:29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOidcUserInfoService {

//    private final UserFeignClient userFeignClient;


    public CustomOidcUserInfo loadUserByUsername(String username) {
//        UserAuthInfo userAuthInfo = null;
//        try {
//            userAuthInfo = userFeignClient.getUserAuthInfo(username);
//            if (userAuthInfo == null) {
//                return null;
//            }
//            return new CustomOidcUserInfo(createUser(userAuthInfo));
//        } catch (Exception e) {
//            log.error("获取用户信息失败", e);
//            return null;
//        }
        return null;
    }

//    private Map<String, Object> createUser(UserAuthInfo userAuthInfo) {
//        return CustomOidcUserInfo.customBuilder()
//                .username(userAuthInfo.getUsername())
//                .nickname(userAuthInfo.getNickname())
//                .status(userAuthInfo.getStatus())
//                .phoneNumber(userAuthInfo.getMobile())
//                .email(userAuthInfo.getEmail())
//                .profile(userAuthInfo.getAvatar())
//                .build()
//                .getClaims();
//    }

}
