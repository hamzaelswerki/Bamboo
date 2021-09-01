package com.example.bamboo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StatusForCheckOut {

    @SerializedName("data")
    @Expose
    private List<CheckOut> data = null;

    public List<CheckOut> getData() {
        return data;
    }

    public void setData(List<CheckOut> data) {
        this.data = data;
    }

}