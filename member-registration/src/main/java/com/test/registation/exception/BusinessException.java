package com.test.registation.exception;


import com.test.registation.constant.BusinessCode;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String description;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.description = null;
    }
    public BusinessException(BusinessCode businessCode) {
        super(businessCode.getMessage());
        this.code = businessCode.getCode();
        this.description = null;
    }
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

}