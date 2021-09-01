package com.example.bamboo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckOut {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("price")
    @Expose
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
