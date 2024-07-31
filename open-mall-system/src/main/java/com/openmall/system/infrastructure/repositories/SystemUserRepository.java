package com.openmall.system.infrastructure.repositories;

import com.openmall.system.domain.entity.SystemUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wuxuan
 * @since 2024/7/30 11:02:07
 */
@Repository
public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 系统用户
     */
    Optional<SystemUser> findByUsername(String username);
}
