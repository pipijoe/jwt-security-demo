package com.example.jwtsecurity.repo;

/**
 * @author Joetao
 * @date 2022/6/9
 */
public interface UserInfoDO {
    Long getId();

    String getName();

    String getUsername();

    Integer getRoleId();

    String getRoleName();

    Integer getState();
}
