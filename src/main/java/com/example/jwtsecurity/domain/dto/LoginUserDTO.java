package com.example.jwtsecurity.domain.dto;

import lombok.*;

/**
 * @author Joetao
 * @Desc 用户，一个用户可以有多个角色
 * @time 2020/3/19 3:18 PM
 * @Email cutesimba@163.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
