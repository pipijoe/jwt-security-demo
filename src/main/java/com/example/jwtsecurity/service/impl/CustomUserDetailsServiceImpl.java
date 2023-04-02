package com.example.jwtsecurity.service.impl;

import com.example.jwtsecurity.config.SecurityProps;
import com.example.jwtsecurity.domain.entity.RoleEntity;
import com.example.jwtsecurity.domain.entity.UserEntity;
import com.example.jwtsecurity.domain.vo.UserDetail;
import com.example.jwtsecurity.repo.IRoleDao;
import com.example.jwtsecurity.repo.IUserDao;
import com.example.jwtsecurity.repo.IUserRoleDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登陆身份认证
 * @author: JoeTao
 * createAt: 2018/9/14
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserDao userDao;
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IUserRoleDao userRoleDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> opt = userDao.findByUsername(username);
        if (!opt.isPresent()){
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserEntity user = opt.get();

        Set<GrantedAuthority> grantedAuthorities = userRoleDao.findByUserId(user.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(roleDao.findById(role.getRoleId()).orElse(new RoleEntity(0L, SecurityProps.ROLE_ANONYMOUS, "匿名用户")).getRoleName()))
                .collect(Collectors.toSet());

        return UserDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getNickname())
                .password(user.getPassword())
                .enabled(user.getState() == 1)
                .authorities(Collections.unmodifiableSet(grantedAuthorities))
                .build();
    }
}
