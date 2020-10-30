package com.example.sportsnews.service;

import android.content.Context;

import com.example.sportsnews.models.Data;
import com.example.sportsnews.network.RequestController;
import com.example.sportsnews.utilities.FunctionBank;


public class ServiceOfSupportArticals {

    public void SupportArticals(Context mContext, FininshLogin fininshLogin, String country, String category, String apiKey) {
        new RequestController<Data>(mContext, false) {


            @Override
            public void onSuccess(Data response) {
                if (response != null)
                    fininshLogin.finish(response);
            }

            @Override
            public void onError(String error) {
                FunctionBank.getInstance().msg(mContext, error);
            }
        }.getArtical(country, category, apiKey);
    }


    public interface FininshLogin {
        void finish(Data registerMember);

    }
}


