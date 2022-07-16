package com.example.onlineshop.utils;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {
    //    private static final String BASE_URL = "http://192.168.1.102:8000/api/v1/";
//    private static final String BASE_URL = "http://172.16.18.2:8000/api/v1/";
    private static final String BASE_URL = "http://onlineshop.iran.liara.run/api/v1/";
    private static Retrofit retrofit;

    public static synchronized API getAPI() {


        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(new ConnectivityInterceptor())
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }


        return retrofit.create(API.class);

    }


}
