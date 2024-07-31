package com.openmall.system.appliction.service;

import com.openmall.system.SystemApplication;
import com.openmall.system.application.dto.CreateSystemUserDTO;
import com.openmall.system.application.service.SystemUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author wuxuan
 * @since 2024/7/31 16:13:11
 */
@ActiveProfiles("dev")
@SpringBootTest(classes = SystemApplication.class)
public class SystemUserServiceTests {

    @Resource
    private SystemUserService systemUserService;

    @Test
    public void testCreate() {
        this.systemUserService.create(new CreateSystemUserDTO("admin", "admin123", "超级管理员", null, null));
    }

}
