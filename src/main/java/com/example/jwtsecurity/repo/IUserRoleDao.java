package com.example.jwtsecurity.repo;

import com.example.jwtsecurity.domain.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author joetao
 */
public interface IUserRoleDao extends JpaRepository<UserRoleEntity, Long>, JpaSpecificationExecutor<UserRoleEntity> {
    /**
     * 用户id查询用户角色对象列表
     *
     * @param userId 用户id
     * @return 用户角色id对应关系
     */
    List<UserRoleEntity> findByUserId(Long userId);

    /**
     * 角色id查询用户角色对象列表
     *
     * @param roleId 角色id
     * @return 用户角色id对应关系
     */
    List<UserRoleEntity> findByRoleId(Long roleId);

    /**
     * 删除用户角色关系
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

}
