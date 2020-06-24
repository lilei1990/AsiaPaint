package com.asia.paint.biz.mine.seller.task;

import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.network.api.TaskService;
import com.asia.paint.base.network.bean.Task;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskCenterAdapter extends BaseGlideAdapter<Task> {
    public TaskCenterAdapter() {
        super(R.layout.item_task_center);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Task task) {
        if (task != null) {
            helper.setText(R.id.tv_task_name, task.name);
            setTarget(helper, task);
            setDone(helper, task);
            helper.setText(R.id.tv_task_process, String.format("完成进度：%s", task.point));
            helper.setGone(R.id.tv_task_status_todo, task.status == TaskService.TASK_TODO);
            helper.setGone(R.id.tv_task_status_done, task.status == TaskService.TASK_DONE);
            helper.setGone(R.id.tv_task_status_overdue, task.status == TaskService.TASK_OVERDUE);
            helper.setText(R.id.tv_task_reward, String.format("完成奖励：%s积分", task.score));
            helper.setText(R.id.tv_task_done_time, String.format("完成时间：%s - %s", task.strtime, task.endtime));
            View view = helper.getView(R.id.layout_task_reward);
            if (task.status == TaskService.TASK_OVERDUE) {
                view.setBackgroundColor(AppUtils.getColor(R.color.color_999999));
            } else {
                view.setBackgroundResource(R.drawable.bg_common_gradient_half);
            }
        }
    }

    private void setTarget(GlideViewHolder helper, Task task) {

        switch (task.type) {
            case TaskService.TYPE_ADD_NEW:
                helper.setText(R.id.tv_task_target_count, String.format("目标拉新人数：%s", task.num));
                break;
            case TaskService.TYPE_SALE:
                helper.setText(R.id.tv_task_target_count, String.format("目标销售额：%s", task.num));
                break;
        }

    }

    private void setDone(GlideViewHolder helper, Task task) {

        switch (task.type) {
            case TaskService.TYPE_ADD_NEW:
                helper.setText(R.id.tv_task_done_count, String.format("已拉新人数：%s", task.join_num));
                break;
            case TaskService.TYPE_SALE:
                helper.setText(R.id.tv_task_done_count, String.format("已完成销售额：%s", task.join_num));
                break;
        }

    }
}
