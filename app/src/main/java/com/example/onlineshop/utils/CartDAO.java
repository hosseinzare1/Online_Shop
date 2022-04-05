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

    @Delete()
    void deleteItem(CartItemModel cartItemModel);

    @Update()
    void updateItem(CartItemModel cartItemModel);

    @Query("SELECT * FROM CartItemModel")
    LiveData<List<CartItemModel>> getItems();

}
