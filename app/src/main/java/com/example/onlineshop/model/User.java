package com.example.onlineshop.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {

    private String name;
    private String number;
    private String password;

    public User(String number, String password, String name) {
        this.name = name;
        this.number = number;
        this.password = password;
    }

    public User(String number, String password) {
        this.number = number;
        this.password = password;
    }
//    @Bindable
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//        notifyPropertyChanged(BR.name);
//    }
//
//    @Bindable
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//
//        this.number = number;
//        notifyPropertyChanged(BR.number);
//
//    }
//
//    @Bindable
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//        notifyPropertyChanged(BR.password);
//
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
