package com.openmall.system.application.service.impl;

import com.openmall.common.exception.system.SystemUserNotExistException;
import com.openmall.system.application.convertors.SystemUserMapper;
import com.openmall.system.application.dto.CreateSystemUserDTO;
import com.openmall.system.application.service.SystemUserService;
import com.openmall.system.domain.entity.SystemUser;
import com.openmall.system.domain.entity.SystemUserDetails;
import com.openmall.system.infrastructure.repositories.SystemUserDetailsRepository;
import com.openmall.system.infrastructure.repositories.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuxuan
 * @since 2024/7/30 11:05:08
 */
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository systemUserRepository;
    private final SystemUserDetailsRepository systemUserDetailsRepository;


    @Override
    public SystemUser getByUsername(String username) {
        return this.systemUserRepository.findByUsername(username)
                .orElseThrow(() -> new SystemUserNotExistException("系统用户不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateSystemUserDTO dto) {
        SystemUser systemUser = SystemUserMapper.INSTANCE.toSystemUser(dto);
        systemUserRepository.save(systemUser);
        SystemUserDetails systemUserDetails = SystemUserMapper.INSTANCE.toSystemUserDetails(dto, systemUser.getId());
        systemUserDetailsRepository.save(systemUserDetails);
    }
}
