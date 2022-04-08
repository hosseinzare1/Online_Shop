package com.example.onlineshop.model;

import java.util.List;

public class Order {

    //user number
    String user;
    List<CartItemModel> orderItems;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<CartItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItemModel> orderItems) {
        this.orderItems = orderItems;
    }
}
