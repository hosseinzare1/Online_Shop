package com.example.onlineshop.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class CartProduct implements Comparable<CartProduct> {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("product")
    int id;

    String name;
    String imageUrl;

    @SerializedName("unit_price")
    public long price = 1;

    public int quantity = 1;

    @SerializedName("unit_discount")
    int discount = 1;

    @NonNull
    @Override
    public String toString() {
        return "user: " + getId() + "\n" +
                "name: " + getName() + "\n" +
                "url: " + getImageUrl() + "\n" +
                "price: " + getPrice() + "\n" +
                "quantity: " + getQuantity() + "\n" +
                "discount: " + getDiscount() + "\n";
    }

    public CartProduct() {
    }

    public CartProduct(int id, String name, String imageUrl, long price, int quantity, int discount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
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


    @Override
    public int compareTo(CartProduct cartProduct) {
        int result = 1;
        if (cartProduct.id == this.id && cartProduct.quantity == this.quantity) {
            result = 0;
        }
        return result;

    }
}
