package com.asia.paint.biz.find.mine.schedule;

import android.util.SparseIntArray;

import com.asia.paint.R;
import com.asia.paint.base.network.api.ServiceService;
import com.asia.paint.base.network.bean.ScheduleService;
import com.asia.paint.base.network.bean.Service;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class ScheduleAdapter extends BaseGlideAdapter<ScheduleService> {
    private SparseIntArray mScheduleImgs;

    {
        mScheduleImgs = new SparseIntArray();
        mScheduleImgs.append(ServiceService.SCHEDULE_CHECK, R.mipmap.ic_schedule_wait);
        mScheduleImgs.append(ServiceService.SCHEDULE_SURE, R.mipmap.ic_schedule_sure);
        mScheduleImgs.append(ServiceService.SCHEDULE_DENY, R.mipmap.ic_schedule_deny);
        mScheduleImgs.append(ServiceService.SCHEDULE_CANCEL, R.mipmap.ic_schedule_cancel);
    }

    public ScheduleAdapter() {
        super(R.layout.item_schedule);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, ScheduleService scheduleService) {
        if (scheduleService != null) {
            Service service = scheduleService._shop;
            if (service != null) {
                helper.loadRoundedCornersImage(R.id.iv_icon, service.image, 4);
                helper.setText(R.id.tv_name, service.shopname);
                helper.setText(R.id.tv_tel, String.format("联系方式：%s", service.tel));
                helper.setText(R.id.tv_time, String.format("工作时间：%s", service.runtime));
                helper.setText(R.id.tv_address, service.address);
            }
            helper.setText(R.id.tv_schedule_time, scheduleService.service_time);
            int resId = mScheduleImgs.get(scheduleService.status);
            helper.setImageResource(R.id.iv_schedule_status, resId);
            helper.setGone(R.id.iv_cancel_schedule, scheduleService.status == ServiceService.SCHEDULE_CHECK);
            helper.addOnClickListener(R.id.iv_cancel_schedule);
        }
    }
}
