package com.example.onlineshop.utils;

import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.Attribute;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.model.Product;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {


    @FormUrlEncoded
    @POST("login/")
    Single<Response<JsonObject>> login(@Field("number") String number, @Field("password") String password);


    @FormUrlEncoded
    @POST("signup/")
    Single<Response<JsonObject>> signup(@Field("name") String name, @Field("number") String number, @Field("password") String password);


    @GET("getall/")
    Single<List<Product>> getAllItems();

    @GET("get_products_by_category/{id}")
    Single<List<Product>> getProductsByCategory(@Path("id") int categoryID);

    @GET("get_same_products/{id}")
    Single<List<Product>> getSameProducts(@Path("id") int productID);

    @GET("get_product/{id}")
    Single<Product> getProduct(@Path("id") int ProductID);

    @GET("get_product_attributes/{id}")
    Single<List<Attribute>> getProductAttributes(@Path("id") int ProductID);

    @GET("get_special_discounts/")
    Single<List<Product>> GetSpecialDiscounts();

    @GET("get_images/{id}")
    Single<List<Image>> getImages(@Path("id") int ProductId);

    @GET("get_comments/{id}")
    Single<List<Comment>> getComments(@Path("id") int ProductID);

    @POST("submit_order/")
    Single<JsonObject> submitOrder(@Body Order order);

    @POST("submit_comment/")
    Single<JsonObject> submitComment(@Body Comment comment);

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

    @GET("search_product/{search_text}")
    Single<List<Product>> searchProducts(@Path("search_text") String searchText);
}
