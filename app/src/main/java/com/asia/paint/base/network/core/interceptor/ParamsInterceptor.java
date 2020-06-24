package com.asia.paint.base.network.core.interceptor;

import com.asia.paint.utils.utils.CacheUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class ParamsInterceptor implements Interceptor {

    private static Map<String, String> mParams;
    private List<String> mFilterUrls;

    public ParamsInterceptor(Map<String, String> params, List<String> filterUrls) {
        mParams = params;
        mFilterUrls = filterUrls;
    }

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        if (!skip(request.url().toString())) {
            FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
            RequestBody originBody = request.body();
            if (originBody instanceof FormBody) {
                FormBody oldFormBody = (FormBody) originBody;
                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBodyBuilder.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }
            }
            if (mParams != null) {
                interceptToken();
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    newFormBodyBuilder.addEncoded(entry.getKey(), entry.getValue());
                }
            }
            FormBody newFormBody = newFormBodyBuilder.build();
            request = request.newBuilder().method(request.method(), newFormBody).build();
        }
        Response response = null;
        try {
            response = chain.proceed(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean skip(String url) {
        return mFilterUrls != null && mFilterUrls.contains(url);
    }

    private void interceptToken() {
        mParams.put("token", CacheUtils.getTk());
    }
}
