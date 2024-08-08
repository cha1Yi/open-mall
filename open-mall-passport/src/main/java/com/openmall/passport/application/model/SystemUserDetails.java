package com.openmall.passport.application.model;

import com.openmall.dubbo.api.system.vo.SystemUserDetailVO;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author wuxuan
 * @since 2024/7/5 10:35:03
 */
@Data
public class SystemUserDetails implements UserDetails, CredentialsContainer {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;


    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 锁定状态
     */
    private boolean locked;

    /*
      启用状态
     */
    private boolean enabled;

    /**
     * 密码过期时间
     */
    private boolean credentialsNonExpired;

    /**
     * 账户过期状态
     */
    private boolean accountNonExpired;

    /**
     * 邮箱
     */
    private String email;


    public SystemUserDetails(SystemUserDetailVO systemUserDetailVO) {
        this.userId = systemUserDetailVO.getId();
        this.username = systemUserDetailVO.getUsername();
        this.nickname = systemUserDetailVO.getNickname();
        this.password = systemUserDetailVO.getPassword();
        this.authorities = Optional.ofNullable(systemUserDetailVO.getGrantedAuthorities())
                .map(one -> one.stream().map(SimpleGrantedAuthority::new).toList())
                .orElse(List.of());
        this.credentialsNonExpired = LocalDateTime.now().isBefore(systemUserDetailVO.getPasswordExpireTime());
        this.accountNonExpired = true;
        this.enabled = systemUserDetailVO.isEnabled();
        this.email = systemUserDetailVO.getEmail();
    }

    public SystemUserDetails(Long userId, String username, String password, Collection<SimpleGrantedAuthority> authorities, boolean locked, boolean enabled, boolean credentialsNonExpired, boolean accountNonExpired, String email,String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;

        this.password = password;
        this.authorities = authorities;
        this.locked = locked;
        this.enabled = enabled;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonExpired = accountNonExpired;
        this.email = email;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
