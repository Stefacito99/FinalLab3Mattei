package ar.edu.utn.frbb.tup.controller.handler;

import org.springframework.http.HttpStatus;

public class CustomApiError {
    private int errorCode;
    private String errorMessage;

    public CustomApiError() {
    }

    public CustomApiError(HttpStatus status, String errorMessage) {
        this.errorCode = status.value();
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}