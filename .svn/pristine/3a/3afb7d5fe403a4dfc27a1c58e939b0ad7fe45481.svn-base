package com.asia.paint.biz.mine.money;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.TaskService;
import com.asia.paint.base.network.bean.ApplyTaskRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author tangkun
 */
public class DistributionTasksViewModel extends BaseViewModel {

	private CallbackDate<ApplyTaskRsp> mTaskRsp = new CallbackDate<>();

	public CallbackDate<ApplyTaskRsp> loadApplyTask() {
		NetworkFactory.createService(TaskService.class)
				.loadApplyTask()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ApplyTaskRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ApplyTaskRsp response) {
						mTaskRsp.setData(response);
					}

				});
		return mTaskRsp;
	}
}
