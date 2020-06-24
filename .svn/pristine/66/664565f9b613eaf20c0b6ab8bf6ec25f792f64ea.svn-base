package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ImageRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author by chenhong14 on 2019-11-19.
 */
@NetworkUrl(Constants.URL)
public interface FileService {

    String HEAD = "head";
    String COMMENT = "comment";
    String POSTER = "poster";
    String SELLER = "seller";
    String TRAIN = "train";

    @Multipart
    @POST("api/index/upload")
    Observable<BaseRsp<ImageRsp>> uploadFile(@Part("type") RequestBody type,@Part MultipartBody.Part file);

    @POST("api/index/upload")
    Observable<BaseRsp<ImageRsp>> uploadFile(@Body RequestBody Body);

    @Multipart
    @POST("api/index/upload")
    Observable<BaseRsp<ImageRsp>> uploadMultiFile(@Part("token") RequestBody token, @Part("type") RequestBody type,
            @PartMap HashMap<String, RequestBody> files);

}
