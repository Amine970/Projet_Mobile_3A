package com.example.recyclerviewapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StandsApi
{
    @GET("index.php")
    Call<List<Stand>> getStands();
}
