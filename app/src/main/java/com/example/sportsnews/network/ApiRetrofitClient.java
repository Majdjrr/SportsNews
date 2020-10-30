package com.example.sportsnews.network;

import android.provider.SyncStateContract;

import com.example.sportsnews.utilities.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Qadamani.Khalid on 5/17/2016.
 */
public class ApiRetrofitClient {

    public static Retrofit getRetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        /**
         * Headers to state their conditions when add
         */
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addNetworkInterceptor(interceptor);
        httpClient.addInterceptor(interceptor);

        httpClient.readTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(WebAddressConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new ErrorHandlingAdapter.ErrorHandlingCallAdapterFactory())
                .client(httpClient.build())
                .build();
    }
}