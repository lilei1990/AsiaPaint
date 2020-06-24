package com.asia.paint.base.network.core.interceptor;


import com.asia.paint.base.constants.Constants;
import com.asia.paint.network.NetworkParamsInterceptor;
import com.asia.paint.utils.utils.CacheUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by chenhong14 on 2019-12-18.
 */
public class TokenParamsInterceptor extends NetworkParamsInterceptor {
    @Override
    protected void addParams(Map<String, String> params) {

    }

    @Override
    protected Map<String, String> getDynamicParams() {
        Map<String, String> params = new HashMap<>();
        params.put("token", CacheUtils.getTk());
        return params;
    }


    @Override
    protected void skipUrls(List<String> skipUrls) {
        skipUrls.add(Constants.URL + "/api/index/upload");
        skipUrls.add(Constants.URL + "/api/user/bind_wx");
    }
}

