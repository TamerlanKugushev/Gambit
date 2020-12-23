package com.example.gambit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("39")
    Call<List<Product>> getProductsList(@Query  ( "page" ) Integer page);

}
