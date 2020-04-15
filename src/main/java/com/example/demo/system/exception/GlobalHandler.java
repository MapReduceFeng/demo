package com.example.demo.system.exception;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.system.entity.Result;
import com.example.demo.system.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHandler.class);

    @ExceptionHandler(Exception.class)
    public Result globalException(Exception exception) {
//        LOGGER.info("异常:", exception);
        if (exception instanceof WebExchangeBindException) {
            return Result.failure(ResultCode.FAILURE, ((WebExchangeBindException) exception).getAllErrors().get(0).getDefaultMessage());
        }else if(exception instanceof AccessDeniedException){
            Result.failure(ResultCode.ROLE_NOT_PERMISSION);
        } else if (exception instanceof JWTDecodeException) {
            Result.failure(ResultCode.NOT_KNOW_TOKEN);
        }
        return Result.failure(ResultCode.NOT_KNOW_ERRE,exception.getMessage());
    }
}
