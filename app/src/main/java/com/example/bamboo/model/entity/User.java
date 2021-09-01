package com.example.bamboo.model.entity;

public class User {

    private String name;
    private String email;
    private String address;
    private String image;
    private String phone_number;
    private String password;
    private String token;
    private String text_image;


    public  User(){
    }

    public  User(String phone_number,String password){
        this.phone_number=phone_number;
        this.password=password;
    }


    public  User(String phone_number,String password,String name,String email,String address){
        this.phone_number=phone_number;
        this.password=password;
        this.name=name;
        this.email=email;
        this.address=address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getText_image() {
        return text_image;
    }

    public void setText_image(String text_image) {
        this.text_image = text_image;
    }
}
