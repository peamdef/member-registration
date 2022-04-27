package com.test.registation.exception;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public static final String ERROR_MSG = "Business Failed";
    private final String code;
    private final String description;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.description = null;

    }
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

}