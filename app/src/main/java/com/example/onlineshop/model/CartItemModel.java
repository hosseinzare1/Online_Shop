package com.example.onlineshop.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItemModel {

    @PrimaryKey(autoGenerate = true)
    int id;


    String name;
    String imageUrl;
    public long price = 1;
    public int count = 1;
    int discount=1;

    public CartItemModel(int id, String name, String imageUrl, long price, int count, int discount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.count = count;
        this.discount = discount;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
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

    public long getCount() {
        return count;
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
