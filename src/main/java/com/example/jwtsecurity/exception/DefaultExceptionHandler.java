package com.example.jwtsecurity.exception;


import com.alibaba.druid.sql.parser.ParserException;
import les.index.governance.dos.vo.ResultCode;
import les.index.governance.dos.vo.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 异常处理类
 *
 * @author jt
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResultJson<?> handleCustomException(CustomException e) {
        log.error(e.getResultJson().toString());
        return e.getResultJson();
    }


    /**
     * 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultJson<String> handleCustomException(ConstraintViolationException e, HttpServletRequest request) {
        log.error(e.getMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson<String> handleCustomException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.getLocalizedMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }


    /**
     * 处理SQL语法异常
     */
    @ExceptionHandler({BadSqlGrammarException.class, ParserException.class})
    public ResultJson<?> handleBadSqlGrammarException(Exception e) {
        log.error(e.toString());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 处理sql 唯一索引约束异常
     */
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResultJson<?> handleSQLIntegrityConstraintViolationException(Exception e) {
        log.error(e.getMessage());
        return ResultJson.failure(ResultCode.CONFLICT, "资源冲突，请检查参数");
    }

}
