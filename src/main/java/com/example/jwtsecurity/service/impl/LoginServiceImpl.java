package com.example.jwtsecurity.service.impl;

import com.example.jwtsecurity.config.AuthCheck;
import com.example.jwtsecurity.config.JwtUtils;
import com.example.jwtsecurity.domain.ResultCode;
import com.example.jwtsecurity.domain.ResultJson;
import com.example.jwtsecurity.domain.entity.UserEntity;
import com.example.jwtsecurity.domain.vo.UserDetail;
import com.example.jwtsecurity.domain.vo.UserVO;
import com.example.jwtsecurity.exception.CustomException;
import com.example.jwtsecurity.repo.IUserDao;
import com.example.jwtsecurity.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Joetao
 * @time 2021/1/26 11:37 上午
 * @Email cutesimba@163.com
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final IUserDao userDao;
    private final JwtUtils jwtTokenUtil;
    private final AuthCheck authCheck;

    public LoginServiceImpl(IUserDao userDao, JwtUtils jwtTokenUtil, AuthCheck authCheck) {
        this.userDao = userDao;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authCheck = authCheck;
    }

    @Override
    public UserVO login(String userName, String password) {
        //用户验证
        final Authentication authentication = authCheck.authenticate(userName, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        UserEntity userEntity = userDao.findById(userDetail.getId()).orElseThrow(() -> new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST)));

        //生成token
        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetail);
        return UserVO.builder()
                .id(userDetail.getId())
                .username(userDetail.getUsername())
                .name(userDetail.getName())
                .isDark(userEntity.getIsDark())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .token(token)
                .expireAt(jwtTokenUtil.getExpirationDateFromToken(token).getTime())
                .refreshToken(refreshToken)
                .auth(true)
                .build();
    }

}
