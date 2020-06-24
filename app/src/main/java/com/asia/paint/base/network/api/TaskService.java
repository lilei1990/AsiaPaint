package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ApplyTaskRsp;
import com.asia.paint.base.network.bean.TaskRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-19.
 */
@NetworkUrl(Constants.URL)
public interface TaskService {

    int TASK_ALL = 0;
    int TASK_TODO = 1;
    int TASK_DONE = 2;
    int TASK_OVERDUE = 3;//已结束过期

    int TYPE_ADD_NEW = 1;//拉新
    int TYPE_SALE = 2;//销售

    @FormUrlEncoded
    @POST("api/SELLER/my_task")
    Observable<BaseRsp<TaskRsp>> loadTask(@Field("p") int page, @Field("status") int status);

    @POST("api/seller/apply_task")
    Observable<BaseRsp<ApplyTaskRsp>> loadApplyTask();

}
