package com.asia.paint.biz.decoration;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Decoration;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * @author tangkun
 */
public class DecorationAdapter extends BaseGlideAdapter<Decoration> {
	public DecorationAdapter() {
		super(R.layout.item_decoration);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, Decoration bean) {
		if (bean != null) {
			if (!TextUtils.isEmpty(bean.title)) {
				helper.setText(R.id.tv_name, bean.title);
			}
			helper.loadImage(R.id.iv_avatar, bean.image);
		}
	}
}
