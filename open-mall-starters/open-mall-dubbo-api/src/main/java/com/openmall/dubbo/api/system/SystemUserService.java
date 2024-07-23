package com.openmall.dubbo.api.system;

import com.openmall.dubbo.api.system.dto.SystemUserDetailVO;

/**
 * 系统用户服务类
 * @author wuxuan
 * @since 2024/7/23 14:45:54
 */
public interface SystemUserService {

    /**
     * 根据用户名获取系统用户信息
     * @param username 用户名
     * @return 系统用户信息
     */
    SystemUserDetailVO getByUsername(String username);
}
