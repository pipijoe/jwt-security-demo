package com.example.jwtsecurity.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Joetao
 * @time 2020/3/20 3:52 PM
 * @Email cutesimba@163.com
 */
@Data
@Builder
public class UserVO {
    private Long id;
    private String username;
    private String name;
    private List<String> roles;
    private Set<Long> roleIds;
    private String token;
    private String refreshToken;

    private Integer isDark;
    private Long expireAt;
    private Boolean auth;
}
