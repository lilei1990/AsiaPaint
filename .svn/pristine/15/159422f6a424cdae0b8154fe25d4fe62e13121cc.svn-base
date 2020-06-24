package com.asia.paint.biz.mine.seller.goals;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.network.bean.StaffSaleDataRsp;
import com.asia.paint.biz.mine.seller.staff.StaffViewModel;
import com.asia.paint.databinding.ActivitySellerGoalsBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;

/**
 * @author by chenhong14 on 2020-01-01.
 */
public class SellerGoalsActivity extends BaseActivity<ActivitySellerGoalsBinding> {
	private StaffViewModel mStaffViewModel;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_seller_goals;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStaffViewModel = getViewModel(StaffViewModel.class);
		mBinding.icBack.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				finish();
			}
		});
		mBinding.tvSellRule.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				WebViewActivity.start(SellerGoalsActivity.this, OtherService.SELL_RULE);
			}
		});
		String time = DateUtils.timeToString(System.currentTimeMillis(), DateUtils.DATE_PATTERN_1);
		mBinding.tvStartTime.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				setStartDate();
			}
		});
		mBinding.tvEndTime.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				setEndDate();
			}
		});
		mBinding.tvStartTime.setText(time);
		mBinding.tvEndTime.setText(time);
		querySaleData(time, time);
	}

	private void querySaleData(String startTime, String endTime) {
		mStaffViewModel.queryStaffSaleData(null, startTime, endTime).setCallback(this::updateSellerData);
	}

	private void updateSellerData(StaffSaleDataRsp dataRsp) {
		if (dataRsp == null) {
			return;
		}
		mBinding.tvScore.setText(String.valueOf(dataRsp.score));
		mBinding.tvDealCount.setText(String.valueOf(dataRsp.order));
		mBinding.tvDealAmount.setText(String.valueOf(dataRsp.money));
		mBinding.tvNewUser.setText(String.valueOf(dataRsp.new_user));
		mBinding.tvNewPartner.setText(String.valueOf(dataRsp.new_seller));
		mBinding.tvSaleRoyalties.setText(String.valueOf(dataRsp.total_score));
		mBinding.tvPartnership.setText(dataRsp.partner_profit);
		mBinding.tvPrimaryCommissionRate.setText(dataRsp.level_2 + "%");
		mBinding.tvSecondaryCommissionRate.setText(dataRsp.level_3 + "%");
	}

	private void setStartDate() {

		String date = mBinding.tvStartTime.getText().toString();

		//时间选择器
		TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选
			// 中事件回调
			String startTime = DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_1);
			mBinding.tvStartTime.setText(startTime);
			String endTime = mBinding.tvEndTime.getText().toString();
			if (!TextUtils.equals(endTime, "结束时间")) {
				querySaleData(startTime, endTime);
			}
		})
				.setCancelColor(AppUtils.getColor(R.color.color_333333))
				.setSubmitColor(AppUtils.getColor(R.color.color_333333))
				.setContentTextSize(18)
				.build();
		if (!TextUtils.equals(date, "开始时间")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_1));
			pvTime.setDate(calendar);
		}
		pvTime.show();
	}

	private void setEndDate() {

		String date = mBinding.tvEndTime.getText().toString();

		//时间选择器
		TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选中事件回调
			String endTime = DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_1);
			mBinding.tvEndTime.setText(endTime);
			String startTime = mBinding.tvStartTime.getText().toString();
			if (!TextUtils.equals(startTime, "开始时间")) {
				querySaleData(startTime, endTime);
			}

		})
				.setCancelColor(AppUtils.getColor(R.color.color_333333))
				.setSubmitColor(AppUtils.getColor(R.color.color_333333))
				.setContentTextSize(18)
				.build();
		if (!TextUtils.equals(date, "结束时间")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_1));
			pvTime.setDate(calendar);
		}
		pvTime.show();
	}

}
