package com.client.api.exception;

public enum ErrorCode {
    INVALID_DATE(01, "Date in ID is invalid"),
    ID_NUMBER_LIMIT(02, "ID number exceeds 12 characters"),
    ID_NUMBER_IS_NOT_A_NUMBER(02, "ID number is not a number"),
    NOT_SA_CITIZEN(02, "Not SA citizen");

    public int code;
    public String status;

    private ErrorCode(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
