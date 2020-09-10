package com.asia.paint.base.container;

import com.asia.paint.android.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.android.databinding.FragmentBaseBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2020-03-01.
 */
public abstract class BaseListFragment extends BaseFragment<FragmentBaseBinding> {

    private BaseViewModel mViewModel;
    protected BaseGlideAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    protected void initView() {
        mViewModel = getViewModel();
        mAdapter = getBaseAdapter();
        mViewModel.resetPage();
        mBinding.rvBaseList.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvBaseList.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mBinding.rvBaseList.setItemAnimator(null);
        mBinding.rvBaseList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadDate();
        }, mBinding.rvBaseList);
        loadDate();
    }


    public abstract BaseGlideAdapter getBaseAdapter();

    public abstract BaseViewModel getViewModel();

    public abstract void loadDate();


}
