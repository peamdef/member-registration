package com.test.registation.utils;

import com.test.registation.constant.StatusCode;
import com.test.registation.model.CommonResponse;

public class CommonResponseUtil {

    public static <T> CommonResponse<T> createResponse (StatusCode statusCode, T data){
        return new CommonResponse<>(statusCode.getCode(),statusCode.getMessage(),data);
    }

    public static <T> CommonResponse<T> createResponse (StatusCode statusCode){
        return new CommonResponse<>(statusCode.getCode(),statusCode.getMessage());
    }
    public static <T> CommonResponse<T> createResponse (String code, String message){
        return new CommonResponse<>(code,message);
    }
}
