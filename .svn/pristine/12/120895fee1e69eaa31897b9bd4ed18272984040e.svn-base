package com.asia.paint.biz.mine.message;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.MessageRsp;
import com.asia.paint.databinding.ActivityMessageBinding;
import com.asia.paint.utils.callback.OnChangeCallback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-02.
 */
public class MessageActivity extends BaseActivity<ActivityMessageBinding> {

    private MessageViewModel mViewModel;
    private MessageAdapter mMessageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "消息通知";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(MessageViewModel.class);
        mBinding.rvMessage.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter = new MessageAdapter();
        mBinding.rvMessage.setAdapter(mMessageAdapter);
        mViewModel.queryMessage(1).setCallback(new OnChangeCallback<MessageRsp>() {
            @Override
            public void onChange(MessageRsp result) {
                mMessageAdapter.replaceData(result.message);
            }
        });
    }
}
