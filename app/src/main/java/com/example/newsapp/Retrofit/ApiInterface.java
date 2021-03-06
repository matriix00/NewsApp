package com.example.newsapp.Retrofit;

import com.example.newsapp.Models.News;
import com.example.newsapp.Models.Website;
import com.example.newsapp.Utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("v2/sources?language=en&apiKey=" + AppConstants.API_KEY)
    Call<Website> getSources();

    @GET
    Call<News>getNewestArticles(@Url String url);
}
