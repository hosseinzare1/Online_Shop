package com.example.onlineshop.utils;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onlineshop.model.Product;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    void insertItem(Product product);

    @Query("DELETE FROM Product WHERE name = :productName")
    void deleteItem(String productName);

    @Query("DELETE FROM Product WHERE id = (select id from Product order by id limit 1)")
    void deleteFirstItem();

    @Query("SELECT * FROM Product")
    List<Product> getItems();

}
