package com.openmall.passport.model;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    public SystemUserDetails(Long userId, String username, String password, Integer dataScope, Long deptId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Set<? extends GrantedAuthority> authorities) {

    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
