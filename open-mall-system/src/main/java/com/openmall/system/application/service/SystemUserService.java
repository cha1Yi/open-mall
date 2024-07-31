package com.openmall.system.application.service;

import com.openmall.system.application.dto.CreateSystemUserDTO;
import com.openmall.system.domain.entity.SystemUser;

/**
 * @author wuxuan
 * @since 2024/7/30 11:05:08
 */
public interface SystemUserService {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 系统用户
     */
    SystemUser getByUsername(String username);

    /**
     * 创建系统用户
     * @param dto 创建系统用户入参
     */
    void create(CreateSystemUserDTO dto);
}
