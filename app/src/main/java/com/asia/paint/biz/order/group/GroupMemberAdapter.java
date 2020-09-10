package com.asia.paint.biz.order.group;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.PinTuan;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * 提交订单评团成员列表适配器
 *
 * @author tangkun
 */
public class GroupMemberAdapter extends BaseGlideAdapter<PinTuan> {
	public GroupMemberAdapter() {
		super(R.layout.item_pintuan_member);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PinTuan item) {
		if (item != null) {
			if (item.avatar != null) {
				helper.loadCircleImage(R.id.iv_avatar, item.avatar);
				if (helper.getLayoutPosition() == 0) {
					helper.setVisible(R.id.tv_owner, true);
					helper.setText(R.id.tv_owner, item.status_text);
				} else {
					helper.setVisible(R.id.tv_owner, false);
					helper.setText(R.id.tv_owner, item.status_text);
				}
			}
		}
	}
}
