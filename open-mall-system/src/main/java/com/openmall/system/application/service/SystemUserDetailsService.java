package com.openmall.system.application.service;

import com.openmall.system.domain.entity.SystemUserDetails;

/**
 * @author wuxuan
 * @since 2024/8/6 15:24:57
 */
public interface SystemUserDetailsService {

    SystemUserDetails getByUserId(Long userId);
}
