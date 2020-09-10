package com.asia.paint.biz.mine.service;

import android.text.TextUtils;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.CustomerMessage;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.recyclerview.BaseGlideMultiAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.biz.AsiaPaintApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class CustomerServiceAdapter extends BaseGlideMultiAdapter<CustomerMessage> {

    private List<CustomerMessage> mMessages;
    private UserInfo mUserInfo;

    public CustomerServiceAdapter() {
        mMessages = new ArrayList<>();
        mUserInfo = AsiaPaintApplication.getUserInfo();
        addItemType(CustomerMessage.SELF, R.layout.item_msg_self);
        addItemType(CustomerMessage.SERVICE, R.layout.item_msg_service);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, CustomerMessage message) {

        if (message == null) {
            return;
        }
        String avatar = isSelf(message) ? mUserInfo.avatar : message.admin_logo;
        helper.loadCircleImage(R.id.iv_avatar, avatar);
        String name = isSelf(message) ? mUserInfo.nickname : message.admin_name;
        helper.setText(R.id.tv_user_name, name);
        helper.setText(R.id.tv_time, message.add_time);
        setText(helper, message);
        loadImage(helper, message);
    }

    private void loadImage(GlideViewHolder helper, CustomerMessage message) {
        helper.loadImage(R.id.iv_image, message.image);
        helper.setGone(R.id.iv_image, !TextUtils.isEmpty(message.image));
    }

    private void setText(GlideViewHolder helper, CustomerMessage message) {
        helper.setText(R.id.tv_message, message.content);
        helper.setGone(R.id.tv_message, !TextUtils.isEmpty(message.content));
    }

    private boolean isSelf(CustomerMessage message) {
        return mUserInfo != null && message.form == CustomerMessage.SELF;
    }

    public void updateData(List<CustomerMessage> messages) {
        updateData(messages, false);
    }

    public void updateData(List<CustomerMessage> messages, boolean clearCache) {
        if (clearCache) {
            mMessages.clear();
        }
        if (messages != null) {
            mMessages.addAll(messages);
        }
        replaceData(new ArrayList<>(mMessages));
        notifyDataSetChanged();
    }

    public void updateData(CustomerMessage message) {
        if (message != null) {
            mMessages.add(message);
        }
        replaceData(new ArrayList<>(mMessages));
        notifyDataSetChanged();
    }
}
