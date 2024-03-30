package com.example.boga.model;

import java.util.List;

public class GaMoiModel {
    boolean success;
    String message;
    List<GaMoi> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GaMoi> getResult() {
        return result;
    }

    public void setResult(List<GaMoi> result) {
        this.result = result;
    }
}
