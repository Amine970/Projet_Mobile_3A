package com.example.recyclerviewapi.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass
{
    private static final String BASE_URL = "http://rly-chrono.fr/api/stands/";
    private static Retrofit getRetroInstance()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static StandsApi getApiService()
    {
        return getRetroInstance().create(StandsApi.class);
    }
}
