package com.example.onlineshop.utils;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlineshop.model.CartItemModel;

import java.util.List;

@Dao
public interface CartDAO {


    @Insert()
    void insertItem(CartItemModel cartItemModel);

    @Query("UPDATE cartitemmodel SET quantity = quantity+1 WHERE name = :itemName")
    void increaseItemCount(String itemName);

    @Query("UPDATE cartitemmodel SET quantity = quantity-1 WHERE name = :itemName AND quantity >1")
    void decreaseItemCount(String itemName);

    @Query("DELETE FROM CartItemModel WHERE name = :itemName")
    void deleteItem(String itemName);

    @Query("SELECT * FROM CartItemModel")
    LiveData<List<CartItemModel>> getItems();

    @Query("SELECT name FROM CartItemModel WHERE name = :pName")
    List<String> isItemExist(String pName);



}
