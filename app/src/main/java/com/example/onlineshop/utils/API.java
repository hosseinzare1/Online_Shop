package com.example.onlineshop.utils;

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
}
