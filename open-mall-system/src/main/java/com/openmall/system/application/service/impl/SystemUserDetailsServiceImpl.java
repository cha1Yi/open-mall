package com.openmall.system.application.service.impl;

import com.openmall.common.exception.system.SystemUserNotExistException;
import com.openmall.system.application.service.SystemUserDetailsService;
import com.openmall.system.domain.entity.SystemUserDetails;
import com.openmall.system.infrastructure.repositories.SystemUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wuxuan
 * @since 2024/8/6 15:25:34
 */
@Service
@RequiredArgsConstructor
public class SystemUserDetailsServiceImpl implements SystemUserDetailsService {

    private final SystemUserDetailsRepository systemUserDetailsRepository;

    @Override
    public SystemUserDetails getByUserId(Long userId) {
        return this.systemUserDetailsRepository.findById(userId)
                .orElseThrow(() -> new SystemUserNotExistException("用户详细数据不存在"));
    }
}
