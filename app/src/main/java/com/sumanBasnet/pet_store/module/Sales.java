package com.sumanBasnet.pet_store.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sales {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("product")
    @Expose
    private List<ProductAdminRows> product = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductAdminRows> getProduct() {
        return product;
    }

    public void setProduct(List<ProductAdminRows> product) {
        this.product = product;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    public class ProductAdminRows {

        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("buyer")
        @Expose
        private String buyer;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productPrice")
        @Expose
        private String productPrice;
        @SerializedName("productImage")
        @Expose
        private String productImage;
        @SerializedName("total")
        @Expose
        private String total;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    }





}
