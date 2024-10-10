package com.poapp.exception;

// import java.time.LocalDateTime;

// public class ErrorResponse<T> {
//     private String responseStatus;
//     private int responseCode;
//     private String errorType;
//     private String stackTrace;
//     private LocalDateTime timestamp;
//     private T data;
//     private boolean success;

//     // Constructors
//     public ErrorResponse(String responseStatus, int responseCode, String errorType, String stackTrace, T data, boolean success) {
//         this.responseStatus = responseStatus;
//         this.responseCode = responseCode;
//         this.errorType = errorType;
//         this.stackTrace = stackTrace;
//         this.timestamp = LocalDateTime.now();
//         this.data = data;
//         this.success = success;
//     }

    
// }


// package com.poapp.exception;

import java.time.LocalDateTime;

public class ErrorResponse<T> {
    private String responseStatus;
    private int responseCode;
    private String errorType;
    private String stackTrace;
    private LocalDateTime timestamp;
    private T data;
    private boolean success;

    // Constructors, getters, setters...

    public ErrorResponse(String responseStatus, int responseCode, String errorType, String stackTrace, T data, boolean success) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.errorType = errorType;
        this.stackTrace = stackTrace;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.success = success;
    }

    // Getters and setters...

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
