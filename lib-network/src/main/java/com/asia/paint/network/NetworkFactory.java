package com.asia.paint.network;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author by chenhong14 on 2019-10-31.
 */
public final class NetworkFactory {

    private Map<String, Retrofit> mRetrofitCache = new ConcurrentHashMap<>();

    private INetworkOptions mOptions;

    private NetworkFactory() {
        mOptions = new NetworkDefaultOptions();
    }

    public static void setOptions(INetworkOptions options) {
        NetworkHolder.INSTANCE.mOptions = options;
    }

    private static final class NetworkHolder {
        private static final NetworkFactory INSTANCE = new NetworkFactory();
    }


    public static <T> T createService(Class<T> clz) {

        Objects.requireNonNull(clz, "NetworkFactory:The Service.class is null");
        String url = null;
        if (clz.isAnnotationPresent(NetworkUrl.class)) {
            NetworkUrl annotation = clz.getAnnotation(NetworkUrl.class);
            Objects.requireNonNull(annotation, "NetworkFactory:The interface Service can not find @interface(NetworkUrl)");
            url = annotation.value();
        }
        return NetworkHolder.INSTANCE.createService(url, clz);
    }


    private <T> T createService(String url, Class<T> clz) {

        Objects.requireNonNull(url, "NetworkFactory:url is null");
        Retrofit retrofit = mRetrofitCache.get(url);
        if (retrofit == null) {
            retrofit = createRetrofit(url);
            mRetrofitCache.put(url, retrofit);
        }
        return retrofit.create(clz);
    }


    private Retrofit createRetrofit(String url) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        configClient(builder);
        configCallAdapterFactory(builder);
        configConverterFactory(builder);
        return builder.build();
    }

    private void configCallAdapterFactory(Retrofit.Builder builder) {
        if (mOptions == null) {
            return;
        }
        List<CallAdapter.Factory> factories = new ArrayList<>();
        mOptions.addCallAdapterFactory(factories);
        for (CallAdapter.Factory factory : factories) {
            if (factory != null) {
                builder.addCallAdapterFactory(factory);
            }
        }
    }

    private void configConverterFactory(Retrofit.Builder builder) {
        if (mOptions == null) {
            return;
        }
        List<Converter.Factory> factories = new ArrayList<>();
        mOptions.addConverterFactory(factories);
        for (Converter.Factory factory : factories) {
            if (factory != null) {
                builder.addConverterFactory(factory);
            }
        }
    }

    private void configClient(Retrofit.Builder retrofitBuilder) {
        if (mOptions == null) {
            return;
        }
        OkHttpClient httpClient = mOptions.getHttpClient();
        OkHttpClient.Builder builder;
        if (httpClient != null) {
            builder = httpClient.newBuilder();
        } else {
            builder = new OkHttpClient.Builder();
        }
        List<Interceptor> interceptors = new ArrayList<>();
        mOptions.addInterceptor(interceptors);
        for (Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                builder.addInterceptor(interceptor);
            }
        }
        List<Interceptor> networkInterceptors = new ArrayList<>();
        mOptions.addNetworkInterceptor(networkInterceptors);
        for (Interceptor interceptor : networkInterceptors) {
            if (interceptor != null) {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        retrofitBuilder.client(builder.build());
    }
}
