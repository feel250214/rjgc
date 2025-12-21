package com.hr.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int code;
    private String message;
    private T data;

    // 成功响应
    public static <T> ResponseDto<T> success(T data) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setCode(200);
        response.setMessage("Success");
        response.setData(data);
        return response;
    }

    // 失败响应
    public static <T> ResponseDto<T> fail(int code, String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}
