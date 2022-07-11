package com.example.onlineshop.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{

    int id;

    //user number
    String user;
    List<CartProduct> order_items;
    String submit_time;
    String submit_date;

    long totalPrice;
    String state;
    int trackingNumber;

    String transferee_name;
    String transferee_number;
    String transferee_address;

    public String getTransferee_name() {
        return transferee_name;
    }

    public void setTransferee_name(String transferee_name) {
        this.transferee_name = transferee_name;
    }

    public String getTransferee_number() {
        return transferee_number;
    }

    public void setTransferee_number(String transferee_number) {
        this.transferee_number = transferee_number;
    }

    public String getTransferee_address() {
        return transferee_address;
    }

    public void setTransferee_address(String transferee_address) {
        this.transferee_address = transferee_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartProduct> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<CartProduct> order_items) {
        this.order_items = order_items;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

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

    @NonNull
    @Override
    public String toString() {
        return "user: " + getUser() + "\n" +
                "time: " + getSubmit_time() + "\n" +
                "date: " + getSubmit_date() + "\n" +
                "price: " + getTotalPrice() + "\n" +
                "name: " + getTransferee_name() + "\n" +
                "number: " + getTransferee_number() + "\n" +
                "address: " + getTransferee_address() + "\n"+
                "orders: "+order_items.get(0);
    }
}
