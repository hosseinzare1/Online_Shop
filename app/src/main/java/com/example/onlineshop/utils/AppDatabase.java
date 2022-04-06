package com.example.onlineshop.utils;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.onlineshop.model.CartItemModel;

@Database(entities = {CartItemModel.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO itemCartDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context,AppDatabase.class,"cart_item_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }


        return instance;
    }

}
