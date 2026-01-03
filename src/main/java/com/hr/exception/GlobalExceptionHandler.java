package com.hr.exception;

import com.hr.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 错误响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handleBusinessException(BusinessException e) {
        logger.error("Business exception: {}", e.getMessage());
        return ResponseDto.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数绑定异常
     *
     * @param e 参数绑定异常
     * @return 错误响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handleBindException(BindException e) {
        logger.error("Bind exception: {}", e.getMessage());
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> handleOtherException(Exception e) {
        logger.error("Other exception: {}", e.getMessage(), e);
        return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }
}