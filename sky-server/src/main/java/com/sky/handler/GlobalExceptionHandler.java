package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    /**
     * 处理参数类型转换异常
     * 当前端传递 "undefined" 或其他无法转换为目标类型的字符串时触发
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error("参数类型转换异常：{}", ex.getMessage());
        String parameterName = ex.getName();
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "未知类型";
        
        // 如果值是 "undefined"，说明前端传递了未定义的值
        if ("undefined".equals(value) || "null".equals(value)) {
            return Result.error("参数 " + parameterName + " 不能为空");
        }
        
        return Result.error("参数 " + parameterName + " 类型错误，期望类型：" + requiredType + "，实际值：" + value);
    }

}
