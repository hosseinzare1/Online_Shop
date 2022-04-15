package com.example.onlineshop.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @PrimaryKey(autoGenerate = true)
    private int historyDB_id;

    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private long price;
    private int discount;

    private String group;
    private String category;


    public int getHistoryDB_id() {
        return historyDB_id;
    }

    public void setHistoryDB_id(int historyDB_id) {
        this.historyDB_id = historyDB_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Product() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Ignore
    public Product(int id, int historyDB_id, String name, String description, String imageUrl, long price, int discount, String group, String category) {
        this.id = id;
        this.historyDB_id = historyDB_id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discount = discount;

        this.group = group;
        this.category = category;
    }
}
