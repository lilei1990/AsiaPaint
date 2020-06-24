package com.asia.paint.biz.mine.settings.account;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CashService;
import com.asia.paint.base.network.bean.CashAccountRsp;
import com.asia.paint.base.network.bean.CashInfoRsp;
import com.asia.paint.base.network.bean.CashRecordRsp;
import com.asia.paint.base.network.bean.TaskRsp;
import com.asia.paint.base.network.bean.TaxRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-15.
 */
public class CashAccountViewModel extends BaseViewModel {
    private CallbackDate<Boolean> mAddCashAccountRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mEditCashAccountRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mDelCashAccountRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mCheckSmsCodeRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mCashRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mUploadReceiptRsp = new CallbackDate<>();
    private CallbackDate<CashAccountRsp> mMyAccountRsp = new CallbackDate<>();
    private CallbackDate<CashInfoRsp> mCashInfoRsp = new CallbackDate<>();
    private CallbackDate<CashRecordRsp> mCashRecordRsp = new CallbackDate<>();
    private CallbackDate<TaxRsp> mTaxRsp = new CallbackDate<>();


    public CallbackDate<Boolean> addCashAccount(int type, String account,
            String name, String bank, String bank_name) {
        NetworkFactory.createService(CashService.class)
                .addCashAccount(type, account, name, bank, bank_name)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddCashAccountRsp.setData(true);
                    }

                });
        return mAddCashAccountRsp;
    }

    public CallbackDate<Boolean> editCashAccount(int id, int type, String account,
            String name, String bank, String bank_name) {
        NetworkFactory.createService(CashService.class)
                .editCashAccount(id, type, account, name, bank, bank_name)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mEditCashAccountRsp.setData(true);
                    }

                });
        return mEditCashAccountRsp;
    }

    public CallbackDate<CashAccountRsp> queryMyAccount() {
        NetworkFactory.createService(CashService.class)
                .queryMyAccount()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CashAccountRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CashAccountRsp response) {
                        mMyAccountRsp.setData(response);
                    }

                });
        return mMyAccountRsp;
    }

    public CallbackDate<Boolean> delCashAccount(int id) {
        NetworkFactory.createService(CashService.class)
                .delCashAccount(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelCashAccountRsp.setData(true);
                    }

                });
        return mDelCashAccountRsp;
    }

    public CallbackDate<Boolean> checkSmsCode(String mobile, String event, String smsCode) {
        NetworkFactory.createService(CashService.class)
                .checkSmsCode(mobile, event, smsCode)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mCheckSmsCodeRsp.setData(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mCheckSmsCodeRsp;
    }

    public CallbackDate<Boolean> cash(String score, int type, int accountId,
            int mode, String payword, String company) {
        NetworkFactory.createService(CashService.class)
                .cash(score, type, accountId, mode, payword, company)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mCashRsp.setData(true);
                    }

                });
        return mCashRsp;
    }

    public CallbackDate<CashInfoRsp> queryCashInfo(int type) {
        NetworkFactory.createService(CashService.class)
                .queryCashInfo(type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CashInfoRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CashInfoRsp response) {
                        mCashInfoRsp.setData(response);
                    }

                });
        return mCashInfoRsp;
    }

    public CallbackDate<CashRecordRsp> cashRecord() {
        NetworkFactory.createService(CashService.class)
                .cashRecord(getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CashRecordRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CashRecordRsp response) {
                        mCashRecordRsp.setData(response);
                    }

                });
        return mCashRecordRsp;
    }

    public CallbackDate<Boolean> uploadReceipt(int id, int invoice_type, String invoice_price, String express_sn, List<String> images) {

        StringBuilder builder = new StringBuilder();
        if (images != null) {
            for (String url : images) {
                builder.append(url).append(",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        NetworkFactory.createService(CashService.class)
                .uploadReceipt(id, invoice_type, invoice_price, express_sn, builder.toString())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mUploadReceiptRsp.setData(true);
                    }

                });
        return mUploadReceiptRsp;
    }

    public CallbackDate<TaxRsp> getTax(String money) {
        NetworkFactory.createService(CashService.class)
                .getTax(money)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<TaxRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TaxRsp response) {
                        mTaxRsp.setData(response);
                    }

                });
        return mTaxRsp;
    }

}
