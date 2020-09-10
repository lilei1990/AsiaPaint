package com.asia.paint.biz.order.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentPintuanBinding;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.MyPinTuan;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.order.group.GroupDetailActivity;
import com.asia.paint.biz.order.mine.detail.OrderDetailActivity;

/**
 * 拼团Fragment
 *
 * @author tangkun
 */
public class PinTuanFragment extends BaseFragment<FragmentPintuanBinding> {

	private static final String KEY_PINTUAN_TYPE_FRAGMENT = "KEY_PINTUAN_TYPE_FRAGMENT";
	private int mType;
	private PinTuanViewModel mPinTuanViewModel;
	private PinTuanAdapter mPinTuanAdapter;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_pintuan;
	}

	public static PinTuanFragment createInstance(int type) {
		PinTuanFragment fragment = new PinTuanFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_PINTUAN_TYPE_FRAGMENT, type);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void argumentsValue(Bundle bundle) {
		mType = bundle.getInt(KEY_PINTUAN_TYPE_FRAGMENT);
	}

	@Override
	protected void initView() {
		mPinTuanViewModel = getViewModel(PinTuanViewModel.class);
		mBinding.rvOrderMine.setLayoutManager(new LinearLayoutManager(mContext));
		mBinding.rvOrderMine.addItemDecoration(new DefaultItemDecoration(14, 12, 14, 0));
		mBinding.rvOrderMine.setItemAnimator(null);
		mPinTuanAdapter = new PinTuanAdapter();
		mPinTuanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			int id = view.getId();
			MyPinTuan dataBean = (MyPinTuan) adapter.getData().get(position);
			switch (id) {
				case R.id.tv_detai_pintuan://拼团详情
					GroupDetailActivity.start(mContext, dataBean.log_id);
					break;
				case R.id.tv_detai_order://订单详情
					OrderDetailActivity.start(mContext, dataBean.order_id);
					break;
			}
		});
		mBinding.rvOrderMine.setAdapter(mPinTuanAdapter);
		mPinTuanAdapter.setOnLoadMoreListener(() -> {
			mPinTuanViewModel.autoAddPage();
			loadOrder();
		}, mBinding.rvOrderMine);

	}

	@Override
	public void onResume() {
		super.onResume();
		mPinTuanViewModel.resetPage();
		loadOrder();
	}

	private void loadOrder() {
		mPinTuanViewModel.loadMyPintuan(mPinTuanViewModel.getCurPage(), mType).setCallback(result -> {
			mPinTuanViewModel.updateListData(mPinTuanAdapter, result);
		});

	}

}
