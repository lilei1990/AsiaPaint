package com.asia.paint.biz.mine.seller.task;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.TaskRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.databinding.FragmentTaskCenterBinding;
import com.asia.paint.utils.callback.OnChangeCallback;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskCenterFragment extends BaseFragment<FragmentTaskCenterBinding> {

    private static final String KEY_TASK_CENTER_TYPE = "KEY_TASK_CENTER_TYPE";
    private int mType;
    private TaskCenterViewModel mViewModel;
    private TaskCenterAdapter mTaskCenterAdapter;

    public static TaskCenterFragment createInstance(int type) {
        TaskCenterFragment fragment = new TaskCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TASK_CENTER_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_TASK_CENTER_TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_center;
    }

    @Override
    protected void initView() {
        mViewModel = getViewModel(TaskCenterViewModel.class);
        mBinding.rvTaskCenter.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvTaskCenter.addItemDecoration(new DefaultItemDecoration(0, 8, 0, 0));
        mTaskCenterAdapter = new TaskCenterAdapter();
        mBinding.rvTaskCenter.setAdapter(mTaskCenterAdapter);
        mViewModel.loadReceipt(1, mType).setCallback(new OnChangeCallback<TaskRsp>() {
            @Override
            public void onChange(TaskRsp result) {
                updateTaskRsp(result);
            }
        });
    }

    private void updateTaskRsp(TaskRsp taskRsp) {
        if (taskRsp == null) {
            return;
        }
        mTaskCenterAdapter.updateData(taskRsp.task);
    }
}
