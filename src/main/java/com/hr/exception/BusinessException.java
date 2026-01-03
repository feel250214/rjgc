package com.hr.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private int code;

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(int code) {
        this.code = code;
    }
}