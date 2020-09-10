package com.asia.paint.biz.mine.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityCustomerServiceBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.CustomerMessage;
import com.asia.paint.base.network.bean.CustomerServiceRsp;
import com.asia.paint.base.network.bean.TextMessageRsp;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.biz.mine.service.history.CustomerServiceHistoryActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class CustomerServiceActivity extends BaseTitleActivity<ActivityCustomerServiceBinding> {

    private static final String KEY_CUSTOMER_SERVICE_ID = "KEY_CUSTOMER_SERVICE_ID";
    private int mServiceId;
    private CustomerServiceAdapter mCustomerServiceAdapter;
    private CustomerServiceViewModel mViewModel;
    private Disposable mSubscribe;
    private Long mLastTime;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        intent.putExtra(KEY_CUSTOMER_SERVICE_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mServiceId = intent.getIntExtra(KEY_CUSTOMER_SERVICE_ID, 0);
    }

    @Override
    protected String middleTitle() {
        return "客服";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding.tvRightText.setText("历史记录");
        mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                CustomerServiceHistoryActivity.start(CustomerServiceActivity.this, 0);
            }
        });

        mViewModel = getViewModel(CustomerServiceViewModel.class);
        mBinding.rvServiceMsg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        mBinding.rvServiceMsg.setItemAnimator(null);
        mCustomerServiceAdapter = new CustomerServiceAdapter();
        mBinding.rvServiceMsg.setAdapter(mCustomerServiceAdapter);
        mSubscribe = Observable.interval(0, 5, TimeUnit.SECONDS).subscribe(aLong -> mViewModel.getMsg(mLastTime).setCallback(result -> {
            if (result != null) {
                mLastTime = result.getTime();
                CustomerServiceRsp serviceRsp = result.getData();
                if (serviceRsp != null) {
                    List<CustomerMessage> data = serviceRsp.data;
                    if (data != null && !data.isEmpty()) {
                        mCustomerServiceAdapter.addData(0, data);
                        mBinding.rvServiceMsg.scrollToPosition(0);
                    }
                }
            }
        }));
        mBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSendView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.tvSend.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String text = getText(mBinding.etContent);
                mViewModel.sendTextMsg(text).setCallback(new OnChangeCallback<TextMessageRsp>() {
                    @Override
                    public void onChange(TextMessageRsp result) {

                    }
                });

       /*         CustomerMessage message = new CustomerMessage();
                message.itemType = CustomerMessage.SELF;
                message.content = text;
                message.add_time = DateUtils.timeToString(System.currentTimeMillis(), DateUtils.DATE_PATTERN_5);
                message.admin_logo = mUserInfo.avatar;
                message.admin_name = mUserInfo.nickname;
                mCustomerServiceAdapter.addData(0, message);*/
                mBinding.etContent.setText("");
                // mBinding.rvServiceMsg.scrollToPosition(0);
            }
        });
        mBinding.ivAddImage.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MatisseActivity.start(CustomerServiceActivity.this, 1, new OnChangeCallback<List<String>>() {
                    @Override
                    public void onChange(List<String> result) {
                        uploadImage(result);
                    }
                });
            }
        });
        updateSendView();
    }

    private void uploadImage(List<String> image) {
        if (image == null || image.isEmpty()) {
            return;
        }
        FileUtils.uploadMultiFile(FileService.POSTER, image).setCallback(new OnChangeCallback<List<String>>() {
            @Override
            public void onChange(List<String> result) {
                if (result != null && result.size() > 0) {
                    mViewModel.sendImageMsg(result.get(0)).setCallback(new OnChangeCallback<TextMessageRsp>() {
                        @Override
                        public void onChange(TextMessageRsp result) {

                        }
                    });
            /*        CustomerMessage message = new CustomerMessage();
                    message.f = CustomerMessage.SELF;
                    message.image = image.get(0);
                    message.add_time = DateUtils.timeToString(System.currentTimeMillis(), DateUtils.DATE_PATTERN_5);
                    message.admin_logo = mUserInfo.avatar;
                    message.admin_name = mUserInfo.nickname;
                    mCustomerServiceAdapter.addData(0, message);
                    mBinding.rvServiceMsg.scrollToPosition(0);*/

                }
            }
        });
    }

    private void updateSendView() {
        String text = getText(mBinding.etContent);
        boolean empty = TextUtils.isEmpty(text);
        mBinding.ivAddImage.setVisibility(empty ? View.VISIBLE : View.GONE);
        mBinding.tvSend.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (mSubscribe != null) {
            mSubscribe.dispose();
        }
        super.onDestroy();
    }
}
