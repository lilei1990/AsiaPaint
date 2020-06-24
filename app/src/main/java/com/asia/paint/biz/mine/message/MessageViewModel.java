package com.asia.paint.biz.mine.message;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.MessageService;
import com.asia.paint.base.network.bean.MessageRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-01.
 */
public class MessageViewModel extends BaseViewModel {

    private CallbackDate<MessageRsp> mMessageRsp = new CallbackDate<>();

    public CallbackDate<MessageRsp> queryMessage(int page) {
        NetworkFactory.createService(MessageService.class)
                .queryMessage(page)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MessageRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MessageRsp response) {
                        mMessageRsp.setData(response);
                    }

                });
        return mMessageRsp;
    }

    public void queryMessageDetail(int msgId) {
        NetworkFactory.createService(MessageService.class)
                .queryMessageDetail(msgId)
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

    public void delMessage(int msgId) {
        NetworkFactory.createService(MessageService.class)
                .delMessage(msgId)
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
