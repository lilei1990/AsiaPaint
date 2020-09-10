package com.asia.paint.biz.mine.seller.recommend;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityRecommendCodeBinding;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.UserDetail;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;
import com.asia.paint.utils.utils.CopyUtils;
import com.asia.paint.utils.utils.QRCode;
import com.bumptech.glide.Glide;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class RecommendCodeActivity extends BaseTitleActivity<ActivityRecommendCodeBinding> {

	private MineViewModel mMineViewModel;
	private String mRecommendNo;

	@Override
	protected String middleTitle() {
		return "我的推荐";
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_recommend_code;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMineViewModel = getViewModel(MineViewModel.class);
		mBinding.tvCopyId.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				boolean copy = CopyUtils.copy(RecommendCodeActivity.this, mRecommendNo);
				if (copy) {
					AppUtils.showMessage("复制成功");
				}
			}
		});
		mMineViewModel.loadSellerInfoDetail().setCallback(this::updateUserInfo);
		//app图片
		mMineViewModel.loadIndexBase().setCallback(result -> {
			Glide.with(this).load(result.tj_bg).into(mBinding.imgBack);
			CacheUtils.put(CacheUtils.KEY_SMZ, result.smz);
		});
	}

	private void updateUserInfo(UserDetail user) {
		if (user == null) {
			return;
		}
		mRecommendNo = Constants.URL + "?sellerId=" + user.code;
		setTextRecommendNo(user.code);
		setRecommendQrCode(mRecommendNo);

	}


	private void setTextRecommendNo(String no) {
		mBinding.tvRecommendId.setText(String.format("我的分销合伙人编号：%s", no));
	}

	private void setRecommendQrCode(String content) {
		Bitmap qrCode = QRCode.createQRCode(content, AppUtils.dp2px(140));
		if (qrCode != null) {
			mBinding.ivQrCode.setImageBitmap(qrCode);
		}
	}
}
