package com.sumanBasnet.pet_store.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("availablepcs")
    @Expose
    private String availablepcs;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orderlimit")
    @Expose
    private String orderlimit;
    @SerializedName("price")
    @Expose
    private String price;


    @SerializedName("seller")
    @Expose
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailablepcs() {
        return availablepcs;
    }

    public void setAvailablepcs(String availablepcs) {
        this.availablepcs = availablepcs;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderlimit() {
        return orderlimit;
    }

    public void setOrderlimit(String orderlimit) {
        this.orderlimit = orderlimit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
