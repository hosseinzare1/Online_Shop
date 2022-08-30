package com.example.onlineshop.utils;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.onlineshop.R;
import com.example.onlineshop.model.CartProduct;
import com.example.onlineshop.model.Product;

@Database(entities = {CartProduct.class, Product.class}, version = 14,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO itemCartDao();

    public abstract HistoryDAO historyDAO();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.cart_database_name))
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }


        return instance;
    }

}
