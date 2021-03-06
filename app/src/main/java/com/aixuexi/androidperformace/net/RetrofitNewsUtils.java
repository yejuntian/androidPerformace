package com.aixuexi.androidperformace.net;


import com.aixuexi.androidperformace.PerformanceApp;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNewsUtils {
    private static final APIService API_SERVICE;

    public static APIService getApiService() {
        return API_SERVICE;
    }

    public static final String HTTP_SPORTSNBA_QQ_COM = "http://sportsnba.qq.com/";

    static {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(PerformanceApp.Companion.getApplication().getCacheDir(), 10 * 1024 * 1024);
        client.cache(cache)
                .eventListenerFactory(OkHttpEventListener.Companion.getFactory())
                .dns(OkHttpDns.Companion.getInstance(PerformanceApp.Companion.getApplication()))
                .addInterceptor(new NoNetInterceptor()).
                addInterceptor(logging);

        final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(HTTP_SPORTSNBA_QQ_COM)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        API_SERVICE = RETROFIT.create(APIService.class);
    }


}
