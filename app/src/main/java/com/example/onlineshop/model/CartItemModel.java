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
     String price = "1";
    String count="1";

    public CartItemModel(int id, String name, String imageUrl, String price, String count) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.count = count;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }



    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
