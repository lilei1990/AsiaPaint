package com.asia.paint.biz.decoration;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.databinding.FragmentDecorationBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.qqtheme.framework.util.LogUtils;

/**
 * 家装宝典
 *
 * @author tangkun
 */
public class DecorationFragment extends BaseFragment<FragmentDecorationBinding> {

	private DecorationAdapter mDecorationAdapter;
	private DecorationViewModel mDecorationViewModel;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_decoration;
	}

	@Override
	protected void initView() {
		mDecorationViewModel = getViewModel(DecorationViewModel.class);
		mDecorationViewModel.resetPage();
		mBinding.rvDecoration.setLayoutManager(new LinearLayoutManager(mContext));
		mDecorationAdapter = new DecorationAdapter();
		mBinding.rvDecoration.setAdapter(mDecorationAdapter);
		mDecorationAdapter.setOnLoadMoreListener(() -> {
			mDecorationViewModel.autoAddPage();
			loadZone();
		}, mBinding.rvDecoration);
		loadZone();
		mDecorationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getActivity(), DecorationDetailActivity.class);
				String id = mDecorationAdapter.getData().get(position).id;
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}

	private void loadZone() {
		mDecorationViewModel.loadNewsInfo(mDecorationViewModel.getCurPage()).setCallback(result -> {
					LogUtils.debug("家装宝典", result.toString());
					mDecorationViewModel.updateListData(mDecorationAdapter, result);
				}
		);
	}
}
