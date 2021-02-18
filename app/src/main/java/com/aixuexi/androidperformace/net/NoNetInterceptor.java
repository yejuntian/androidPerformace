package com.aixuexi.androidperformace.net;

import com.aixuexi.androidperformace.PerformanceApp;
import com.aixuexi.androidperformace.utils.Utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NoNetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if(!Utils.isNetworkConnected(PerformanceApp.Companion.getApplication())){
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}
