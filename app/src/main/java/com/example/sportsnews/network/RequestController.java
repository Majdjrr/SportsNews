package com.example.sportsnews.network;

import android.content.Context;
import android.util.Log;


import com.example.sportsnews.models.Data;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Response;

public abstract class RequestController<T> {
    private Context mContext;
    private APImanager apiManager;
    private ProgressBarService oprogressDialogService;

    public RequestController(Context mContext, boolean withProgress) {
        this.mContext = mContext;
        apiManager = ApiRetrofitClient.getRetrofitClient().create(APImanager.class);
        if (withProgress) {
            oprogressDialogService = new ProgressBarService(mContext);
            oprogressDialogService.startProgressBar();
        }
    }


    public abstract void onSuccess(T response);

    public abstract void onError(String error);

    private void endProgressDialog() {
        if (oprogressDialogService != null) {
            oprogressDialogService.stopProgressBar();
        }
    }


    private RequestBody getRequestBodyJson(Object driver) {
        return RequestBody.create(new Gson().toJson(driver), okhttp3.MediaType.parse("application/json; charset=utf-8"));
    }


    public void getArtical(String country, String category, String apiKey) {

        ErrorHandlingAdapter.MyCall<Data> login = apiManager.postLogin(country, category, apiKey);
        login.enqueue(new ErrorHandlingAdapter.MyCallback<Data>() {
            @Override
            public void success(Response<Data> response) {
                endProgressDialog();
                onSuccess((T) response.body());

            }

            @Override
            public void serverError(Response<?> response) {
                endProgressDialog();
                onError(response.message());
            }

            @Override
            public void networkError(IOException e) {
                endProgressDialog();
                Log.e("majd", e.getMessage());
                onError(e.getMessage());

            }

            @Override
            public void gsonException(String error) {
                endProgressDialog();
                onError(error);
            }

            @Override
            public void unAuthroized(String error) {
                endProgressDialog();
            }


        });


    }


}
