package com.asia.paint.base.widgets.task;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewTaskPanelBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.network.bean.Task;
import com.asia.paint.biz.mine.seller.task.TaskCenterActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskPanel extends BaseFrameLayout<ViewTaskPanelBinding> {
    private TaskPanelAdapter mTaskPanelAdapter;

    public TaskPanel(@NonNull Context context) {
        super(context);
    }

    public TaskPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mBinding.layoutAllTask.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, TaskCenterActivity.class));
            }
        });
        mBinding.rvTask.setLayoutManager(new LinearLayoutManager(mContext));
        mTaskPanelAdapter = new TaskPanelAdapter(mContext);
        mBinding.rvTask.setAdapter(mTaskPanelAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_task_panel;
    }

    public void setTaskCount(int count) {
        mBinding.tvTaskCount.setVisibility(count > 0 ? VISIBLE : GONE);
        mBinding.tvTaskCount.setText(String.valueOf(count));
    }

    public void updateTask(List<Task> tasks) {
        mTaskPanelAdapter.updateData(tasks, true);
    }


}
