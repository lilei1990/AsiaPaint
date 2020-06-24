package com.asia.paint.biz.find.mine.schedule;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.api.ServiceService;
import com.asia.paint.base.network.bean.ScheduleService;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.find.service.ServiceViewModel;
import com.asia.paint.databinding.FragmentScheduleBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class ScheduleFragment extends BaseFragment<FragmentScheduleBinding> {

    private ServiceViewModel mServiceViewModel;
    private ScheduleAdapter mScheduleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initView() {
        mServiceViewModel = getViewModel(ServiceViewModel.class);
        mServiceViewModel.resetPage();
        mBinding.rvSchedule.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvSchedule.setItemAnimator(null);
        mBinding.rvSchedule.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mScheduleAdapter = new ScheduleAdapter();
        mScheduleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ScheduleService scheduleService = mScheduleAdapter.getData(position);
            if (scheduleService != null && view.getId() == R.id.iv_cancel_schedule) {
                mServiceViewModel.cancelScheduleService(scheduleService.id).setCallback(result -> {
                    if (result) {
                        scheduleService.status = ServiceService.SCHEDULE_CANCEL;
                        mScheduleAdapter.notifyItemChanged(position);
                    }
                });
            }
        });
        mBinding.rvSchedule.setAdapter(mScheduleAdapter);
        mScheduleAdapter.setOnLoadMoreListener(() -> {
            mServiceViewModel.autoAddPage();
            loadScheduleService();
        }, mBinding.rvSchedule);
        loadScheduleService();
    }

    private void loadScheduleService() {
        mServiceViewModel.loadScheduleService("", mServiceViewModel.getCurPage())
                .setCallback(result -> mServiceViewModel.updateListData(mScheduleAdapter, result));
    }
}
