package com.asia.paint.biz.mine.favorites;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.FavoritesRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * @author by chenhong14 on 2019-11-14.
 */
public class FavoritesAdapter extends BaseGlideAdapter<FavoritesRsp.Favorite> {

	public boolean isShowCheckBox = false;

	public void setShowCheckBox(boolean showCheckBox) {
		isShowCheckBox = showCheckBox;
	}

	public FavoritesAdapter() {
		super(R.layout.item_favorites);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, FavoritesRsp.Favorite item) {

		if (item != null && item._goods != null) {
			if (isShowCheckBox) {
				helper.setGone(R.id.cb_check, true);
				if (item.isSelected) {
					helper.setImageResource(R.id.cb_check, R.mipmap.ic_checkbox_selected);
				} else {
					helper.setImageResource(R.id.cb_check, R.mipmap.ic_checkbox_normal);
				}
			} else {
				helper.setGone(R.id.cb_check, false);
			}
			if (item._goods.default_image != null && item._goods.default_image.size() > 0) {
				helper.loadRoundedCornersImage(R.id.iv_icon, item._goods.default_image.get(0), 4);
			}
			helper.setText(R.id.tv_name, item._goods.goods_name);
			helper.setText(R.id.tv_price, PriceUtils.getPrice(item._goods.price));
		}

	}

	/**
	 * 选中数量
	 */
	public int getSelectedCount() {
		int count = 0;
		for (FavoritesRsp.Favorite item : getData()) {
			if (item.isSelected)
				count++;
		}
		return count;
	}

	/**
	 * 选中id
	 */
	public String getSelectedId() {
		String ids = "";
		for (FavoritesRsp.Favorite item : getData()) {
			if (item.isSelected)
				if (TextUtils.isEmpty(ids)) {
					ids = item.goods_id + "";
				} else {
					ids = ids + "," + item.goods_id;
				}
		}
		return ids;
	}
}
