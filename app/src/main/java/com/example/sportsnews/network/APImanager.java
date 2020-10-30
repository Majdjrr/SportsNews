package com.example.sportsnews.network;


import com.example.sportsnews.models.Data;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APImanager {

    @GET(WebAddressConstants.URLARTICAL)
    ErrorHandlingAdapter.MyCall<Data> postLogin(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey" )String apiKey);


}
