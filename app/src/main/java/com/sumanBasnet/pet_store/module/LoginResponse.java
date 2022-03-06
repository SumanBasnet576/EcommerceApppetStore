package com.sumanBasnet.pet_store.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("favourite")
    @Expose
    private String favourite;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("myproducts")
    @Expose
    private String myproducts;



    @SerializedName("sales")
    @Expose
    private String sales;

    @SerializedName("cart")
    @Expose
    private String cart;


    @SerializedName("order")
    @Expose
    private String order;


    @SerializedName("address")
    @Expose
    private String address;


    public String getAddress() {
        return address;
    }

    @SerializedName("myBalance")
    @Expose
    private String myBalance;

    public String getMyBalance() {
        return myBalance;
    }

    public void setMyBalance(String myBalance) {
        this.myBalance = myBalance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMyproducts() {
        return myproducts;
    }

    public void setMyproducts(String myproducts) {
        this.myproducts = myproducts;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
