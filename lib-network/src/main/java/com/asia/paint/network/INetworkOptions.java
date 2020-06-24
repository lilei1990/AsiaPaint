package com.asia.paint.network;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @author by chenhong14 on 2019-12-17.
 */
public interface INetworkOptions {

    OkHttpClient getHttpClient();

    void addCallAdapterFactory(List<CallAdapter.Factory> factories);

    void addConverterFactory(List<Converter.Factory> factories);

    void addInterceptor(List<Interceptor> interceptors);

    void addNetworkInterceptor(List<Interceptor> interceptors);
}
