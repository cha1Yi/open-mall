package com.openmall.system.infrastructure.repositories;

import com.openmall.system.domain.entity.SystemUserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxuan
 * @since 2024/7/31 16:04:28
 */
@Repository
public interface SystemUserDetailsRepository extends CrudRepository<SystemUserDetails, Long> {
}
