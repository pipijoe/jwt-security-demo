package com.example.jwtsecurity.repo;

import com.example.jwtsecurity.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
 * @author joetao
 */
public interface IRoleDao extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
    List<RoleEntity> findByIdIn(Set<Long> ids);
}
