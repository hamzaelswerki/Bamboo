package com.example.bamboo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StatusForCarts {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private DataForCarts data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataForCarts getData() {
        return data;
    }

    public void setData(DataForCarts data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}