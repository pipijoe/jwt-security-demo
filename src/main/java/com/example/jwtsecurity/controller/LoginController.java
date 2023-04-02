package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.domain.ResultJson;
import com.example.jwtsecurity.domain.dto.LoginUserDTO;
import com.example.jwtsecurity.domain.vo.UserVO;
import com.example.jwtsecurity.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Joetao
 * @date 2023/2/20
 */
@RestController
@RequestMapping("/api/v1")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public ResultJson<UserVO> login(@Valid @RequestBody LoginUserDTO user) {
        UserVO userVO = loginService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(userVO);
    }

}
