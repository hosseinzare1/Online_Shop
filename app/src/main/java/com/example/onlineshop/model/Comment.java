package com.example.onlineshop.model;

import androidx.databinding.ObservableField;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;

public class Comment {

    public int id;

    private int product;
    public int rating;
    private String title;
    private String text;

    private String user_name;
    private String user;

    private String submit_time;
    private String submit_date;

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment() {
    }





    public Comment(String text, String title, int rating, String user_name, String user, int product, String submit_date, String submit_time) {

        this.text = text;
        this.title = title;
        this.rating = rating;
        this.user_name = user_name;
        this.user = user;
        this.product = product;
        this.submit_date = submit_date;
        this.submit_time = submit_time;
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
