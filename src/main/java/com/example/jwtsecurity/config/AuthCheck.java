package com.example.jwtsecurity.config;

import com.example.jwtsecurity.domain.ResultCode;
import com.example.jwtsecurity.domain.ResultJson;
import com.example.jwtsecurity.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Joetao
 * @date 2023/2/27
 */
@Component
@Slf4j
public class AuthCheck {
    private final AuthenticationManager authenticationManager;

    public AuthCheck(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            log.error("登录异常，{}", e.getLocalizedMessage());
            throw new CustomException(ResultJson.failure(ResultCode.UNAUTHORIZED, "用户名或密码无效"));
        }
    }
}
