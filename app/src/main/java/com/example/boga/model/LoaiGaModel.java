package com.example.boga.model;

import java.util.List;

public class LoaiGaModel {
    boolean success;
    String message;
    List<LoaiGa> result;

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

    public List<LoaiGa> getResult() {
        return result;
    }

    public void setResult(List<LoaiGa> result) {
        this.result = result;
    }
}
