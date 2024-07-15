package com.openmall.passport.application.service;

import com.openmall.passport.application.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * 系统用户信息加载
 * @author wuxuan
 * @since 2024/7/12 09:24:12
 */
@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SystemUserDetails(1L, "wuxuan", PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("ch199407") , 1, 1L, true, true, true, true, new HashSet<>());
    }
}
