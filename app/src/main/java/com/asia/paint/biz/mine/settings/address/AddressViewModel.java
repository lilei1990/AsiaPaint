package com.asia.paint.biz.mine.settings.address;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.AddressService;
import com.asia.paint.base.network.bean.AddressRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AddressViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mAddAddressResult = new CallbackDate<>();
    private CallbackDate<Boolean> mAddDefaultResult = new CallbackDate<>();
    private CallbackDate<Boolean> mDelResult = new CallbackDate<>();
    private CallbackDate<Boolean> mEditResult = new CallbackDate<>();
    private CallbackDate<AddressRsp> mAddressResult = new CallbackDate<>();

    public CallbackDate<AddressRsp> queryAddress(int page) {
        NetworkFactory.createService(AddressService.class)
                .queryAddress(page)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<AddressRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(AddressRsp response) {
                        mAddressResult.setData(response);
                    }

                });
        return mAddressResult;
    }

    public CallbackDate<Boolean> addAddress(String consignee, String tel,
            String area, String address) {
        NetworkFactory.createService(AddressService.class)
                .addAddress(consignee, tel, area, address)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddAddressResult.setData(true);
                    }

                });
        return mAddAddressResult;
    }

    public CallbackDate<Boolean> editAddress(int addressId, String consignee, String tel,
            String area, String address) {
        NetworkFactory.createService(AddressService.class)
                .editAddress(addressId, consignee, tel, area, address)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mEditResult.setData(true);
                    }

                });
        return mEditResult;
    }


    public CallbackDate<Boolean> addDefaultAddress(int addressId) {
        NetworkFactory.createService(AddressService.class)
                .addDefaultAddress(addressId)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddDefaultResult.setData(true);
                    }

                });
        return mAddDefaultResult;
    }

    public CallbackDate<Boolean> delAddress(int addressId) {
        NetworkFactory.createService(AddressService.class)
                .delAddress(addressId)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelResult.setData(true);
                    }

                });
        return mDelResult;
    }
}
