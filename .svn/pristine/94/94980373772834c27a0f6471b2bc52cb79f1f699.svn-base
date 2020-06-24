package com.asia.paint.biz.mine.settings.feedback;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.FeedbackService;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-01.
 */
public class FeedbackViewModel extends BaseViewModel {

    public void submitFeedback(String content) {
        NetworkFactory.createService(FeedbackService.class)
                .submitFeedback(content)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                    }

                });
    }
}
