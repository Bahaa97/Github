package com.bahaa.github.data.network;


import com.bahaa.github.MyApplication;
import com.bahaa.github.data.network.interfaces.Api;
import com.bahaa.github.ui.activities.base.BaseActivity;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://api.github.com/users/";
    public static Retrofit retrofit = null;
    public static OkHttpClient okHttpClient = null;
    static Gson gson;

    public static Api webService() {
        BaseActivity.getInstance().showProgressDialog();
        if (retrofit == null) {
            gson = new Gson();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new NetworkInterceptor(MyApplication.getContext()))
                    .addInterceptor(logging)
                    .connectTimeout(3600, TimeUnit.SECONDS)
                    .readTimeout(3600, TimeUnit.SECONDS)
                    .writeTimeout(3600, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(Api.class);
    }

    public static void cancelAllRequests() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }

    public static okhttp3.Call retryLastRequest() {
        List<okhttp3.Call> list = null;
        Call call = null;
        if (okHttpClient != null) {
            list = okHttpClient.dispatcher().queuedCalls();
            if (list.size() != 0) {
                call = list.get(list.size() - 1);
            }
        }
        return call;
    }
}

