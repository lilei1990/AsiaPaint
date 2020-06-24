package com.asia.paint.biz.mine.message;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Message;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class MessageAdapter extends BaseGlideAdapter<Message> {
    public MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Message message) {
        if (message != null) {
            helper.setText(R.id.tv_msg_title, message.title);
            helper.setText(R.id.tv_msg_content, message.content);
            helper.setText(R.id.tv_msg_date, message.add_time);
        }
    }
}
