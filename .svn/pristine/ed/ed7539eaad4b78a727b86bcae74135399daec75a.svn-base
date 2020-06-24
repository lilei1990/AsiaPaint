package com.asia.paint.base.model;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.network.bean.RichTextRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2020-01-02.
 */
public class OtherViewModel extends BaseViewModel {

    private CallbackDate<RichTextRsp> mResultRsp = new CallbackDate<>();

    public CallbackDate<RichTextRsp> loadRichText(String title) {
        NetworkFactory.createService(OtherService.class)
                .loadRichText(title)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<RichTextRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(RichTextRsp response) {
                        mResultRsp.setData(response);
                    }

                });
        return mResultRsp;
    }
}
