package com.example.bamboo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatus {
    @SerializedName("data")
    @Expose
    private List<OrderData> data = null;

    public List<OrderData> getData() {
        return data;
    }

    public void setData(List<OrderData> data) {
        this.data = data;
    }

}
