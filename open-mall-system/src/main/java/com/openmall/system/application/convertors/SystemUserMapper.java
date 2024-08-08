package com.openmall.system.application.convertors;

import com.openmall.dubbo.api.system.vo.SystemUserDetailVO;
import com.openmall.system.application.dto.CreateSystemUserDTO;
import com.openmall.system.domain.entity.SystemUser;
import com.openmall.system.domain.entity.SystemUserDetails;
import com.openmall.system.domain.entity.enums.UserStateEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * @author wuxuan
 * @since 2024/7/31 10:59:30
 */
@Mapper(imports = {UserStateEnum.class, LocalDateTime.class})
public interface SystemUserMapper {
    SystemUserMapper INSTANCE = Mappers.getMapper(SystemUserMapper.class);

    @Mapping(target = "passwordExpireTime", source = "systemUser.passwordEffectiveTime", qualifiedByName = "getPasswordExpireTime")
    @Mapping(target = "locked", source = "systemUser.userState", qualifiedByName = "isLocked")
    @Mapping(target = "enabled", source = "systemUser.userState", qualifiedByName = "isEnabled")
    @Mapping(target = "grantedAuthorities", ignore = true)
    SystemUserDetailVO toSystemUserDetailVO(SystemUser systemUser, SystemUserDetails systemUserDetails);


    @Named("isLocked")
    default boolean isLocked(UserStateEnum state) {
        return state == UserStateEnum.DISABLED;
    }

    @Named("isEnabled")
    default boolean isEnabled(UserStateEnum state) {
        return state == UserStateEnum.NORMAL;
    }

    @Named("getPasswordExpireTime")
    default LocalDateTime getPasswordExpireTime(LocalDateTime passwordEffectiveTime) {
        return passwordEffectiveTime.plusMonths(3);
    }


    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userState", expression = "java(UserStateEnum.NORMAL)")
    @Mapping(target = "passwordEffectiveTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "password", qualifiedByName = "passwordEncode", source = "password")
    @Mapping(target = "username", source = "username")
    SystemUser toSystemUser(CreateSystemUserDTO dto);

    PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Named("passwordEncode")
    default String passwordEncode(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "nickname", source = "dto.nickname")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "avatar", source = "dto.avatar")
    SystemUserDetails toSystemUserDetails(CreateSystemUserDTO dto, Long userId);

}
