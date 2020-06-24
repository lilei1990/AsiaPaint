package com.asia.paint.biz.find.service;

import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Service;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.util.GPSUtils;
import com.asia.paint.databinding.FragmentServiceBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * 换新服务
 * @author by chenhong14 on 2019-12-09.
 */
public class ServiceFragment extends BaseFragment<FragmentServiceBinding> implements GPSUtils.OnLocationResultListener {

    private ServiceAdapter mServiceAdapter;
    private Location mLocation;
    private ServiceViewModel mServiceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GPSUtils.getInstance(mActivity).getLngAndLat(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView() {
        mServiceViewModel = getViewModel(ServiceViewModel.class);
        mServiceViewModel.resetPage();
        mBinding.tvSort.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

            }
        });
        mBinding.tvArea.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

            }
        });
        mBinding.rvService.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvService.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mServiceAdapter = new ServiceAdapter();
        mBinding.rvService.setAdapter(mServiceAdapter);
        mServiceAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Service service = mServiceAdapter.getData().get(position);
            if (view.getId() == R.id.tv_schedule) {
                ServiceScheduleDialog dialog = new ServiceScheduleDialog();
                dialog.setOnServiceScheduleListener((time, name, tel, remake) -> mServiceViewModel.scheduleService(service.id, name, tel, time, remake));
                dialog.show(mContext);
            }
        });
        mServiceAdapter.setOnLoadMoreListener(() -> {
            mServiceViewModel.autoAddPage();
            loadService();
        }, mBinding.rvService);
        loadService();
    }

    private void loadService() {
        if (mLocation == null || mServiceViewModel == null) {
            return;
        }
        mServiceViewModel.loadService(mLocation.getLongitude(), mLocation.getLatitude(), "", "")
                .setCallback(result -> mServiceViewModel.updateListData(mServiceAdapter, result));
    }

    @Override
    public void onLocationResult(Location location) {
        getLocation(location);
    }

    @Override
    public void OnLocationChange(Location location) {
        getLocation(location);
    }

    private void getLocation(Location location) {
        if (mLocation == null && location != null) {
            mLocation = location;
            loadService();
        } else {
            GPSUtils.getInstance(mActivity).removeListener();
        }
    }

    @Override
    public void onDestroy() {
        GPSUtils.getInstance(mActivity).removeListener();
        super.onDestroy();
    }
}
