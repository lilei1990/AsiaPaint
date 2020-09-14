package com.asia.paint.base.widgets.task;

import android.content.Context;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Task;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskPanelAdapter extends BaseGlideAdapter<Task> {
    public TaskPanelAdapter(Context context) {
        super(context,R.layout.item_task_panel);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Task task) {
        if (task != null) {
            helper.setText(R.id.tv_task_name, task.name);
            helper.setText(R.id.tv_task_process, task.point);
            helper.setText(R.id.tv_task_score, String.format("%s积分", task.score));
        }
    }
}
