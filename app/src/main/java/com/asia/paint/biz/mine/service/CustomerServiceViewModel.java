package com.asia.paint.biz.mine.service;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ChatService;
import com.asia.paint.base.network.bean.CustomerServiceRsp;
import com.asia.paint.base.network.bean.TextMessageRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-01.
 */
public class CustomerServiceViewModel extends BaseViewModel {

    private CallbackDate<TextMessageRsp> mSendImgRsp = new CallbackDate<>();
    private CallbackDate<TextMessageRsp> mSendTextRsp = new CallbackDate<>();
    private CallbackDate<BaseRsp<CustomerServiceRsp>> mgetMsgRsp = new CallbackDate<>();
    private CallbackDate<CustomerServiceRsp> mMsgHistoryRsp = new CallbackDate<>();

    private Observable<BaseRsp<TextMessageRsp>> sendMsg(int type, String content, String image) {
        return NetworkFactory.createService(ChatService.class)
                .sendMsg(type, content, image)
                .compose(new NetworkObservableTransformer<>());
    }

    public CallbackDate<TextMessageRsp> sendImageMsg(String image) {
        sendMsg(ChatService.CHAT_IMAGE, "", image)
                .subscribe(new DefaultNetworkObserver<TextMessageRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TextMessageRsp response) {
                        mSendImgRsp.setData(response);
                    }

                });
        return mSendImgRsp;
    }

    public CallbackDate<TextMessageRsp> sendTextMsg(String content) {
        sendMsg(ChatService.CHAT_TEXT, content, null)
                .subscribe(new DefaultNetworkObserver<TextMessageRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TextMessageRsp response) {
                        mSendTextRsp.setData(response);
                    }

                });
        return mSendTextRsp;
    }

    public CallbackDate<BaseRsp<CustomerServiceRsp>> getMsg(Long lasttime) {
        NetworkFactory.createService(ChatService.class)
                .getMsg(lasttime, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CustomerServiceRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CustomerServiceRsp response) {

                    }

                    @Override
                    public void onComplete(BaseRsp<CustomerServiceRsp> baseRsp) {
                        mgetMsgRsp.setData(baseRsp);
                    }
                });
        return mgetMsgRsp;
    }

    public CallbackDate<CustomerServiceRsp> getHistoryMsg() {
        NetworkFactory.createService(ChatService.class)
                .getHistoryMsg("", getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CustomerServiceRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CustomerServiceRsp response) {
                        mMsgHistoryRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mMsgHistoryRsp.setData(null);
                    }
                });

        return mMsgHistoryRsp;

    }
}
