package com.example.onlineshop.model;

import java.util.List;

public class Order {

    //user number
    String user;
    List<CartItemModel> order_items;
    String submit_time;
    String submit_date;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<CartItemModel> getOrderItems() {
        return order_items;
    }

    public void setOrderItems(List<CartItemModel> orderItems) {
        this.order_items = orderItems;
    }
}
