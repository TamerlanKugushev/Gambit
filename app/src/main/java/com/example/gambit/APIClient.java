package com.example.gambit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static <T> T CreateService(Class<T> serviceClass){

        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl ( APIConstants.BASE_URL )
                .addConverterFactory ( GsonConverterFactory.create () )
                .build ();

        return retrofit.create ( serviceClass );

    }

}
