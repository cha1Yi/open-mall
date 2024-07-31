package com.openmall.passport.application.service;

import com.openmall.dubbo.api.system.SystemUserServiceApi;
import com.openmall.dubbo.api.system.dto.SystemUserDetailVO;
import com.openmall.passport.application.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 系统用户信息加载
 * @author wuxuan
 * @since 2024/7/12 09:24:12
 */
@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    @DubboReference
    private SystemUserServiceApi systemUserServiceApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUserDetailVO systemUserDetailVO = this.systemUserServiceApi.getByUsername(username);
        return new SystemUserDetails(systemUserDetailVO);
    }
}
