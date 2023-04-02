package com.example.jwtsecurity.repo;

import com.example.jwtsecurity.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author joetao
 */
public interface IUserDao extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);

    /**
     * 分页查询用户
     * @param pageable
     * @return
     */
    @Query(value = "select aa.id, aa.nickname as name, aa.state, aa.username, bb.role_name_zh as roleName, bb.id as roleId from ( select a.id, a.nickname, a.username, a.state, b.role_id from sys_user a, sys_user_role b where  a.id = b.user_id) aa LEFT JOIN sys_role bb on aa.role_id = bb.id", nativeQuery = true)
    Page<UserInfoDO> queryUsers(@Nullable Specification<UserInfoDO> var1, Pageable pageable);


}
