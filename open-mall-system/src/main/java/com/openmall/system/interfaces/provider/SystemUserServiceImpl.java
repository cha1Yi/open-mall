package com.openmall.system.interfaces.provider;

import com.openmall.dubbo.api.system.SystemUserService;
import com.openmall.dubbo.api.system.dto.SystemUserDetailVO;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统用户相关服务api的实现
 * @author wuxuan
 * @since 2024/7/23 15:11:48
 */
@DubboService
public class SystemUserServiceImpl implements SystemUserService {
    @Override
    public SystemUserDetailVO getByUsername(String username) {

        return null;
    }
}
