package com.example.onlineshop.utils;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onlineshop.model.Product;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    public void insertItem(Product product);

    @Query("DELETE FROM Product WHERE name = :productName")
    public void deleteItem(String productName);

    @Query("DELETE FROM Product WHERE id = (select id from Product order by id limit 1)")
    public void deleteFirstItem();

    @Query("SELECT * FROM Product")
    public List<Product> getItems();

}
