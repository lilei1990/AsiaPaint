package com.asia.paint.network;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by chenhong14 on 2019-12-17.
 */
public class NetworkDefaultOptions implements INetworkOptions {
    @Override
    public OkHttpClient getHttpClient() {
        return null;
    }

    @Override
    public void addCallAdapterFactory(List<CallAdapter.Factory> factories) {
        factories.add(RxJava2CallAdapterFactory.create());
    }

    @Override
    public void addConverterFactory(List<Converter.Factory> factories) {
        factories.add(GsonConverterFactory.create());
    }

    @Override
    public void addInterceptor(List<Interceptor> interceptors) {
        //网络日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptors.add(loggingInterceptor);
        }
    }

    @Override
    public void addNetworkInterceptor(List<Interceptor> interceptors) {

    }
}
