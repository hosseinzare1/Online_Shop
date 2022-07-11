package com.example.onlineshop.utils;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onlineshop.model.CartProduct;

import java.util.List;

@Dao
public interface CartDAO {


    @Insert()
    void insertItem(CartProduct cartProduct);

    @Query("UPDATE CartProduct SET quantity = quantity+1 WHERE name = :itemName")
    void increaseItemCount(String itemName);

    @Query("UPDATE CartProduct SET quantity = quantity-1 WHERE name = :itemName AND quantity >1")
    void decreaseItemCount(String itemName);

    @Query("DELETE FROM CartProduct WHERE name = :itemName")
    void deleteItem(String itemName);

    @Query("SELECT * FROM CartProduct")
    LiveData<List<CartProduct>> getItems();

    @Query("SELECT name FROM CartProduct WHERE name = :pName")
    List<String> isItemExist(String pName);

    @Query("DELETE FROM CartProduct")
    void removeAllCartItems();



}
