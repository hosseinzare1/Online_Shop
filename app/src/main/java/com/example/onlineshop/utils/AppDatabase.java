package com.example.onlineshop.utils;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Product;

@Database(entities = {CartItemModel.class, Product.class}, version = 13)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO itemCartDao();

    public abstract HistoryDAO historyDAO();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "cart_item_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }


        return instance;
    }

}
