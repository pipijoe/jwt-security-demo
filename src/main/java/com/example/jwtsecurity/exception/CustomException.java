package com.example.jwtsecurity.exception;


import com.example.jwtsecurity.domain.ResultJson;
import lombok.Getter;

/**
 * Created at 2018/8/24.
 *
 * @author Joetao
 */
@Getter
public class CustomException extends RuntimeException {
    private final ResultJson<Object> resultJson;

    public CustomException(ResultJson<Object> resultJson) {
        this.resultJson = resultJson;
    }
}
