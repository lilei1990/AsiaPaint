package com.asia.paint.biz.order.group;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.PinTuanDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * 拼团详情成员列表适配器
 *
 * @author tangkun
 */
public class GroupDetailMemberAdapter extends BaseGlideAdapter<PinTuanDetail.PintuanBean> {
	public GroupDetailMemberAdapter() {
		super(R.layout.item_group_detail_member);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PinTuanDetail.PintuanBean item) {
		if (item != null) {
			if (!TextUtils.isEmpty(item.top) && item.top.equals("团长")) {
				helper.setText(R.id.tv_owner, item.top);
				helper.setVisible(R.id.tv_owner, true);
			} else {
				helper.setText(R.id.tv_owner, item.top);
				helper.setVisible(R.id.tv_owner, false);
			}
			//paystatus：1支付了 0待支付
			if (item.paystatus == 0) {
				helper.setImageResource(R.id.iv_avatar, R.mipmap.ic_member_unpay);
			} else {
				helper.loadCircleImage(R.id.iv_avatar, item.avatar);
			}
		}
	}
}
