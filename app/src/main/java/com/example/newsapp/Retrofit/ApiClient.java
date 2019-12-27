package com.example.newsapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements Serializable {

    public static final String Base_URL = "http://newsapi.org";


    public static  Retrofit getApiClient(){

        Retrofit retrofit;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new  Retrofit.Builder().baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;

    }

    public static  String getApiUrl(String source, String apiKey){
        StringBuilder apiUrl = new StringBuilder("http://newsapi.org//v2/top-headlines?sources=");

        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKey)
                .toString();
    }

}