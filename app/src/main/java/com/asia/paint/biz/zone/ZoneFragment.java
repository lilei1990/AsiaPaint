package com.asia.paint.biz.zone;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentZoneBinding;
import com.asia.paint.base.container.BaseFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class ZoneFragment extends BaseFragment<FragmentZoneBinding> {

    private ZoneAdapter mZoneAdapter;
    private ZoneViewModel mZoneViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initView() {
        mZoneViewModel = getViewModel(ZoneViewModel.class);
        mZoneViewModel.resetPage();
        mBinding.rvZone.setLayoutManager(new LinearLayoutManager(mContext));
        mZoneAdapter = new ZoneAdapter();
        mBinding.rvZone.setAdapter(mZoneAdapter);
        mZoneAdapter.setOnLoadMoreListener(() -> {
            mZoneViewModel.autoAddPage();
            loadZone();
        }, mBinding.rvZone);
        loadZone();
    }

    private void loadZone() {
        mZoneViewModel.loadZoneInfo(mZoneViewModel.getCurPage()).setCallback(result ->
                mZoneViewModel.updateListData(mZoneAdapter, result));
    }
}
