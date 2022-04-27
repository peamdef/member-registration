package com.test.registation.constant;

public enum BusinessCode {
    RGTE1000("RGTE1000","Access denied"),
    RGTE1001("RGTE1001","Username already exist"),
    RGTE1002("RGTE1002","salary lower than 15000"),
    RGTE1003("RGTE1003","Wallet already exist"),
    RGTE1004("RGTE1004","Not found in database"),
    WLTE1001("WLTE1001","Wallet not found"),

    USDB1000("USDB1000","Cannot connect database")
    ;
    private String code;
    private String message;

    BusinessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
