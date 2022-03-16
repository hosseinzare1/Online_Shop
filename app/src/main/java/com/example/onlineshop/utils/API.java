package com.example.onlineshop.utils;

import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {


    @FormUrlEncoded
    @POST("login/")
    Single<Response<JsonObject>> login(@Field("number") String number, @Field("password") String password);


    @FormUrlEncoded
    @POST("signup/")
    Single<Response<JsonObject>> signup(@Field("name") String name, @Field("number") String number, @Field("password") String password);


    @GET("getall/")
    Single<List<HomeItem>> getAllItems();


    @GET("get_details/{id}")
    Single<Product> getDetails(@Path("id") int ProductID);

    @GET("get_images/{id}")
    Single<List<Image>> getImages(@Path("id") int ProductId);

    @GET("get_account_details/{number}")
    Single<Account> getAccountDetails(@Path("number") String number);

    @FormUrlEncoded
    @POST("update_account/{number}")
    Single<Account> updateAccount(@Path("number") String number
            , @Field("name") String name
            , @Field("number") String newNumber
            , @Field("address") String address
            , @Field("email") String email
            , @Field("password") String password);


    @GET("get_groups/")
    Single<List<Group>> getGroups();

    @GET("get_categorys/{id}")
    Single<List<Category>> getCategorys(@Path("id") int groupID);
}
