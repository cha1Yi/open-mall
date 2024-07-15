package com.openmall.passport.infrastructure.repository;

import com.openmall.passport.domain.entity.LoginLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxuan
 * @since 2024/7/15 10:28:37
 */
@Repository
public interface LoginLogRepository extends CrudRepository<LoginLog, Long> {
}
