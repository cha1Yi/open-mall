package com.openmall.system.interfaces.provider;

import com.openmall.dubbo.api.system.SystemUserServiceApi;
import com.openmall.dubbo.api.system.vo.SystemUserDetailVO;
import com.openmall.system.application.service.SystemUserDetailsService;
import com.openmall.system.application.service.SystemUserService;
import com.openmall.system.application.convertors.SystemUserMapper;
import com.openmall.system.domain.entity.SystemUser;
import com.openmall.system.domain.entity.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统用户相关服务api的实现
 * @author wuxuan
 * @since 2024/7/23 15:11:48
 */
@DubboService
@RequiredArgsConstructor
public class SystemUserServiceApiProvider implements SystemUserServiceApi {

    private final SystemUserService systemUserService;

    private final SystemUserDetailsService systemUserDetailsService;

    @Override
    public SystemUserDetailVO getByUsername(String username) {
        SystemUser systemUser = this.systemUserService.getByUsername(username);
        SystemUserDetails systemUserDetails = this.systemUserDetailsService.getByUserId(systemUser.getId());
        return SystemUserMapper.INSTANCE.toSystemUserDetailVO(systemUser,systemUserDetails);
    }
}
