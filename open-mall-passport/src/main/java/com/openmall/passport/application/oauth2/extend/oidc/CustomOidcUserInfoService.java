package com.openmall.passport.application.oauth2.extend.oidc;

import com.openmall.dubbo.api.system.SystemUserServiceApi;
import com.openmall.dubbo.api.system.vo.SystemUserDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @DubboReference
    private SystemUserServiceApi systemUserServiceApi;


    public CustomOidcUserInfo loadUserByUsername(String username) {
        try {
            SystemUserDetailVO systemUserDetailVO = this.systemUserServiceApi.getByUsername(username);
            if (systemUserDetailVO == null) {
                return null;
            }
            return new CustomOidcUserInfo(createUser(systemUserDetailVO));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return null;
        }
    }

    private Map<String, Object> createUser(SystemUserDetailVO vo) {
        return CustomOidcUserInfo.customBuilder()
                .username(vo.getUsername())
                .nickname(vo.getNickname())
                .locked(vo.isLocked())
                .mobile(vo.getMobile())
                .enabled(vo.isEnabled())
                .email(vo.getEmail())
                .build()
                .getClaims();
    }

}
