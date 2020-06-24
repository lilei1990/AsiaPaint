package com.asia.paint.biz.mine.seller.task;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.TaskService;
import com.asia.paint.base.network.bean.TaskRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskCenterViewModel extends BaseViewModel {

    private CallbackDate<TaskRsp> mTaskRsp = new CallbackDate<>();

    public CallbackDate<TaskRsp> loadReceipt(int page, int type) {
        NetworkFactory.createService(TaskService.class)
                .loadTask(page, type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<TaskRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TaskRsp response) {

                        mTaskRsp.setData(response);
                    }

                });
        return mTaskRsp;
    }
}
