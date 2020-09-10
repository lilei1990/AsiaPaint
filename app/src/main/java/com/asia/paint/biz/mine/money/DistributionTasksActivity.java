package com.asia.paint.biz.mine.money;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityDistributionTasksBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.ApplyTaskRsp;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * 分销任务
 *
 * @author tangkun
 */
public class DistributionTasksActivity extends BaseActivity<ActivityDistributionTasksBinding> {

	private DistributionTasksGoodsAdapter mDistributionTasksGoodsAdapter;
	private DistributionTaskCouponsAdapter mDistributionTaskCouponsAdapter;
	private DistributionTasksViewModel mViewModel;
	private ApplyTaskRsp mApplyTaskRsp;

	public static void start(Activity activity) {
		Intent intent = new Intent(activity, DistributionTasksActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_distribution_tasks;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "分销任务";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = getViewModel(DistributionTasksViewModel.class);
		mViewModel.resetPage();
		mBinding.rvDistributionTasksGoods.setLayoutManager(new LinearLayoutManager(this));
		mBinding.rvDistributionTasksCoupons.setLayoutManager(new LinearLayoutManager(this));
		mDistributionTasksGoodsAdapter = new DistributionTasksGoodsAdapter();
		mDistributionTaskCouponsAdapter = new DistributionTaskCouponsAdapter();
		mBinding.rvDistributionTasksGoods.setAdapter(mDistributionTasksGoodsAdapter);
		mBinding.rvDistributionTasksCoupons.setAdapter(mDistributionTaskCouponsAdapter);
		//立即支付
		mBinding.btnPay.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mApplyTaskRsp != null && mApplyTaskRsp.task != null)
					OrderCheckoutActivity.start(DistributionTasksActivity.this, OrderService.APPLY_TASK, mApplyTaskRsp.task.id, 1);
			}
		});
		loadApplyTask();
	}

	private void loadApplyTask() {
		mViewModel.loadApplyTask().setCallback(result -> updateTaskRsp(result));
	}

	private void updateTaskRsp(ApplyTaskRsp taskRsp) {
		if (taskRsp == null) {
			return;
		}
		mApplyTaskRsp = taskRsp;
		mDistributionTasksGoodsAdapter.updateData(taskRsp.goods);
		mDistributionTaskCouponsAdapter.updateData(taskRsp.bonus);
	}
}
