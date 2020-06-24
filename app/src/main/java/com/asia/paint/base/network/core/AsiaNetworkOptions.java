package com.asia.paint.base.network.core;

import com.asia.paint.base.network.core.interceptor.TokenParamsInterceptor;
import com.asia.paint.network.NetworkDefaultOptions;

import java.util.List;

import okhttp3.Interceptor;

/**
 * @author by chenhong14 on 2019-12-17.
 */
public class AsiaNetworkOptions extends NetworkDefaultOptions {

    @Override
    public void addInterceptor(List<Interceptor> interceptors) {
        super.addInterceptor(interceptors);
        interceptors.add(new TokenParamsInterceptor());
    }
}
