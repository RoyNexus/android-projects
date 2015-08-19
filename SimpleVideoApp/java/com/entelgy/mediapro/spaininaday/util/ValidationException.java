package com.entelgy.mediapro.spaininaday.util;


public class ValidationException extends Exception {

    private int code;

    public ValidationException() {
        super();
    }
    public ValidationException(int code) {
        new ValidationException();
        setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
