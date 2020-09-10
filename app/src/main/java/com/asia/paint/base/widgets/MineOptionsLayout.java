package com.asia.paint.base.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewMineOptionsBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.biz.mine.coupon.CouponActivity;
import com.asia.paint.biz.mine.favorites.FavoritesActivity;
import com.asia.paint.biz.mine.money.MoneyActivity;
import com.asia.paint.biz.mine.seller.score.ScoreActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class MineOptionsLayout extends BaseFrameLayout<ViewMineOptionsBinding> {

	public MineOptionsLayout(@NonNull Context context) {
		super(context);
	}

	public MineOptionsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MineOptionsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initView() {
		mBinding.viewCoupon.setOptionIcon(R.mipmap.ic_mine_coupon);
		mBinding.viewCoupon.setOptionDescription("优惠券");
		mBinding.viewCoupon.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mContext.startActivity(new Intent(mContext, CouponActivity.class));
			}
		});
		setCouponCount(0);

		mBinding.viewRestMoney.setOptionIcon(R.mipmap.ic_mine_rest_money);
		mBinding.viewRestMoney.setOptionDescription("余额");
		mBinding.viewRestMoney.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mContext.startActivity(new Intent(mContext, MoneyActivity.class));
			}
		});
		setRestMoney("0.00");

		mBinding.viewRestMoneySeller.setOptionIcon(R.mipmap.ic_mine_rest_money);
		mBinding.viewRestMoneySeller.setOptionDescription("余额");
		mBinding.viewRestMoneySeller.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mContext.startActivity(new Intent(mContext, MoneyActivity.class));
			}
		});
		setRestMoney("0.00");


		mBinding.viewMineLike.setOptionIcon(R.mipmap.ic_mine_like);
		mBinding.viewMineLike.setOptionDescription("收藏");
		mBinding.viewMineLike.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mContext.startActivity(new Intent(mContext, FavoritesActivity.class));
			}
		});
		setMineLikeCount(0);

		mBinding.viewSellerScore.setOptionIcon(R.mipmap.ic_seller_score);
		mBinding.viewSellerScore.setOptionDescription("提现");
		mBinding.viewSellerScore.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mContext.startActivity(new Intent(mContext, ScoreActivity.class));
			}
		});
		setMineScoreCount(0 + "");
	}

	public void isSeller(boolean seller) {
		mBinding.viewSellerScore.setVisibility(seller ? VISIBLE : GONE);
		mBinding.viewRestMoneySeller.setVisibility(seller ? VISIBLE : GONE);
		mBinding.viewRestMoney.setVisibility(seller ? GONE : VISIBLE);
	}

	public void setCouponCount(int count) {
		mBinding.viewCoupon.setOptionContent(String.valueOf(count));
	}

	public void setRestMoney(String restMoney) {
		mBinding.viewRestMoney.setOptionContent(restMoney + "元");
		mBinding.viewRestMoneySeller.setOptionContent(restMoney + "元");
	}

	public void setMineLikeCount(int count) {
		mBinding.viewMineLike.setOptionContent(String.valueOf(count));
	}

	public void setMineScoreCount(String count) {
		mBinding.viewSellerScore.setOptionContent(count + "");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.view_mine_options;
	}
}
