package com.asia.paint.biz.find.service;


import com.asia.paint.R;
import com.asia.paint.base.network.bean.Service;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class ServiceAdapter extends BaseGlideAdapter<Service> {

    public ServiceAdapter() {
        super(R.layout.item_service);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Service service) {
        if (service != null) {
            helper.loadRoundedCornersImage(R.id.iv_icon, service.image, 4);
            helper.setText(R.id.tv_name, service.shopname);
            helper.setText(R.id.tv_tel, String.format("联系方式：%s", service.tel));
            helper.setText(R.id.tv_time, String.format("工作时间：%s", service.runtime));
            helper.setText(R.id.tv_address, service.address);
            helper.setText(R.id.tv_distance, service.distance);
            helper.addOnClickListener(R.id.tv_schedule);
        }
    }
}
