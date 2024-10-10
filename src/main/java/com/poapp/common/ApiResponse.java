package com.poapp.common;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String responseStatus;
    private int responseCode;
    private String errorType;  // Optional: for errors
    private String stackTrace; // Optional: for errors
    private LocalDateTime timestamp;
    private T data;
    private boolean success;

    // Constructor for success responses
    public ApiResponse(String responseStatus, int responseCode, T data, boolean success) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.errorType = null;  // Not needed in success case
        this.stackTrace = null; // Not needed in success case
    }

    // Constructor for error responses
    public ApiResponse(String responseStatus, int responseCode, String errorType, String stackTrace, T data, boolean success) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.errorType = errorType;
        this.stackTrace = stackTrace;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.success = success;
    }

    // Getters and setters
    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
