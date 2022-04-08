package com.example.onlineshop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class CartItemModel {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("product")
    int id;

    @Expose(deserialize = false,serialize = false)
    String name;
    @Expose(deserialize = false,serialize = false)
    String imageUrl;
    public long price = 1;
    public int quantity = 1;
    int discount = 1;

    public CartItemModel() {
    }

    public CartItemModel(int id, String name, String imageUrl, long price, int count, int discount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = count;
        this.discount = discount;
    }


    public void setPrice(long price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
