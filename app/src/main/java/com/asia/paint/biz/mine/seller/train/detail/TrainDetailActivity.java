package com.asia.paint.biz.mine.seller.train.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityTrainDetailBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.TrainDetail;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.mine.seller.train.TrainViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.bumptech.glide.Glide;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class TrainDetailActivity extends BaseActivity<ActivityTrainDetailBinding> {

	private static final String KEY_TRAIN_ID = "KEY_TRAIN_ID";
	private int mTrainId;


	public static void start(Context context, int trainId) {
		Intent intent = new Intent(context, TrainDetailActivity.class);
		intent.putExtra(KEY_TRAIN_ID, trainId);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mTrainId = intent.getIntExtra(KEY_TRAIN_ID, 0);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_train_detail;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TrainViewModel trainViewModel = getViewModel(TrainViewModel.class);
		mBinding.icBack.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				finish();
			}
		});
		mBinding.wvView.setWebViewClient(new WebViewClient());
		mBinding.wvView.setBackgroundColor(AppUtils.getColor(R.color.color_EDEDED));
		WebSettings webSettings = mBinding.wvView.getSettings();
		//不支持屏幕缩放
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		//不显示webview缩放按钮
		webSettings.setDisplayZoomControls(false);
		trainViewModel.queryTrainDetail(mTrainId).setCallback(this::updateData);
	}

	private void updateData(TrainDetail trainDetail) {
		if (trainDetail == null) {
			return;
		}
		ImageUtils.load(mBinding.ivIcon, trainDetail.image);
		mBinding.tvTitle.setText(trainDetail.name);
		mBinding.tvDesc.setText(trainDetail.desc);
		if (!TextUtils.isEmpty(trainDetail.video)) {
			mBinding.wvView.setVisibility(View.GONE);
			mBinding.rlVideo.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(trainDetail.image))
				Glide.with(this).load(trainDetail.image).into(mBinding.ivVideo);
		} else {
			mBinding.wvView.setVisibility(View.VISIBLE);
			mBinding.rlVideo.setVisibility(View.GONE);
			String content = trainDetail.content;
			if (!TextUtils.isEmpty(content)) {
				//图片适应手机屏幕
				content = content.replace("<img", "<img style=\"display: block;max-width:100%;\"");
			}
			mBinding.wvView.loadData(content, "text/html", "UTF-8");
		}

		mBinding.rlVideo.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				PlayVideoActivity.start(TrainDetailActivity.this, trainDetail.video);
			}
		});
	}
}
