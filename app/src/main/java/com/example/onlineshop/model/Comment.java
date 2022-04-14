package com.example.onlineshop.model;

import androidx.databinding.ObservableField;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;

public class Comment {

    private int product;

    public int rating;
    private String title;
    private String text;

    private String user_name;
    private String user;

    public Comment() {
    }

    public Comment( String text, String title, int rating, String user_name, String user, int product) {

        this.text = text;
        this.title = title;
        this.rating = rating;
        this.user_name = user_name;
        this.user = user;
        this.product = product;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
