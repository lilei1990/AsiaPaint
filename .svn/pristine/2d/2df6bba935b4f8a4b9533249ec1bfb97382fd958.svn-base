package com.asia.paint.biz.shop.flash;

import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.FlashGoods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * @author by chenhong14 on 2019-11-05.
 */
public class FlashGoodsAdapter extends BaseGlideAdapter<FlashGoods> {

	private OnClickChildListener mChildListener;
	private String mCategoryName;

	public FlashGoodsAdapter(String categoryName) {
		super(R.layout.item_flash_goods);
		this.mCategoryName = categoryName;
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, FlashGoods goods) {
		String iconUrl = "";
		if (goods.default_image != null && goods.default_image.size() > 0) {
			iconUrl = goods.default_image.get(0);
		}
		helper.loadImage(R.id.iv_goods_icon, iconUrl);
		helper.setText(R.id.tv_goods_name, goods.goods_name);
		if (!TextUtils.isEmpty(mCategoryName)) {
			helper.setText(R.id.tv_shop_hour1, goods.showHour);
			helper.setText(R.id.tv_shop_minute1, goods.showMinute);
			helper.setText(R.id.tv_shop_second1, goods.showSecond);

			if (mCategoryName.equals(FlashGoodsFragment.CATEGORY_FLASH_SALE)) {
				helper.setText(R.id.tv_timer_content, "后秒杀结束");
				helper.setText(R.id.btn_add_cart, "立即购买");
			} else if (mCategoryName.equals(FlashGoodsFragment.CATEGORY_GROUP)) {
				helper.setText(R.id.tv_timer_content, "后秒杀拼团");
				helper.setText(R.id.btn_add_cart, "发起拼团");
			}
		}
		String sellCount = String.format("剩余库存 %s", goods.stock);
		helper.setText(R.id.tv_sell_count, sellCount);
		helper.setText(R.id.tv_goods_price, PriceUtils.getPriceTrimZero(goods.price));
		TextView tvMarketPrice = helper.itemView.findViewById(R.id.tv_goods_price_market);
		tvMarketPrice.setText(PriceUtils.getPriceTrimZero(goods.market_price));
		tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间加横线
		tvMarketPrice.getPaint().setAntiAlias(true);//抗锯齿
		helper.addOnClickListener(R.id.btn_add_cart);
	}

	public void setOnChildListener(OnClickChildListener childListener) {
		mChildListener = childListener;
	}

	public interface OnClickChildListener {
		void onAddCart(FlashGoods goods);

		void onCollect(FlashGoods goods);
	}
}
