package com.asia.paint.biz.mine.receipt;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.network.bean.ReceiptRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-07.
 */
public class ReceiptViewModel extends BaseViewModel {

    private CallbackDate<String> mLoadReceiptResult = new CallbackDate<>();
    private CallbackDate<Integer> mAddReceiptRsp = new CallbackDate<>();
    private CallbackDate<ReceiptRsp> mLoadReceiptRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mDelReceipt = new CallbackDate<>();
    private CallbackDate<Boolean> mEditReceipt = new CallbackDate<>();

    public CallbackDate<ReceiptRsp> loadReceipt(int page) {
        NetworkFactory.createService(ReceiptService.class)
                .loadReceipt(page)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ReceiptRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ReceiptRsp response) {
                        mLoadReceiptRsp.setData(response);
                    }

                });
        return mLoadReceiptRsp;
    }

    public CallbackDate<Integer> addReceipt(int receipt, String title, int type, String sn,
            String bank, String bank_sn, String address, String company_tel, String phone, String email, int is_default) {
        NetworkFactory.createService(ReceiptService.class)
                .addReceipt(receipt, title, type, sn, bank, bank_sn, address, company_tel, phone, email, is_default)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(Integer response) {
                        mAddReceiptRsp.setData(response);
                    }

                });
        return mAddReceiptRsp;
    }

    public CallbackDate<Boolean> editReceipt(int id, int receipt, String title, int type, String sn,
            String bank, String bank_sn, String address, String company_tel, String tel, String email, int is_default) {
        NetworkFactory.createService(ReceiptService.class)
                .editReceipt(id, receipt, title, type, sn, bank, bank_sn, address, company_tel, tel, email, is_default)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mEditReceipt.setData(true);
                    }

                });
        return mEditReceipt;
    }

    public CallbackDate<Boolean> delReceipt(int id) {
        NetworkFactory.createService(ReceiptService.class)
                .delReceipt(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelReceipt.setData(true);
                    }

                });
        return mDelReceipt;
    }

}
