package com.asia.paint.biz.mine.seller.store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.databinding.DialogClipBoardBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.bumptech.glide.Glide;

/**
 * 分享的剪贴板弹框
 *
 * @author tangkun
 */
public class ClipBoardDialog extends BaseDialogFragment<DialogClipBoardBinding> {

	private static final String KEY_GOODS = "KEY_GOODS";
	private Goods mGoods;
	private OnChangeCallback<String> mChangeCallback;

	public static ClipBoardDialog createInstance(Goods goods) {
		ClipBoardDialog dialog = new ClipBoardDialog();
		Bundle bundle = new Bundle();
		bundle.putSerializable(KEY_GOODS, goods);
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
	protected void argumentsValue(Bundle bundle) {
		mGoods = (Goods) bundle.getSerializable(KEY_GOODS);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.dialog_clip_board;
	}

	@Override
	protected void initView() {
		if (mGoods != null) {
			if (mGoods.default_image != null && mGoods.default_image.size() > 0) {
				Glide.with(mContext).load(mGoods.default_image.get(0)).placeholder(R.mipmap.ic_default).into(mBinding.ivIcon);
			}
			if (!TextUtils.isEmpty(mGoods.goods_name)) {
				mBinding.tvName.setText(mGoods.goods_name);
			} else {
				mBinding.tvName.setText("");
			}
			mBinding.tvPrice.setText(PriceUtils.getPrice(mGoods.price));
		}
		//查看详情
		mBinding.tvCheckDetail.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				dismiss();
				if (mGoods != null)
					GoodsDetailActivity.start(mContext, mGoods.goods_id);
			}
		});
		mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				dismiss();
			}
		});
	}

	@Override
	protected int getDialogWidth() {
		return AppUtils.dp2px(288);
	}

	public void setChangeCallback(OnChangeCallback<String> changeCallback) {
		mChangeCallback = changeCallback;
	}
}
