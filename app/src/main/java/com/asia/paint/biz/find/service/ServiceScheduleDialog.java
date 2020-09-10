package com.asia.paint.biz.find.service;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogServiceScheduleBinding;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.android.databinding.DialogServiceScheduleBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class ServiceScheduleDialog extends BaseBottomDialogFragment<DialogServiceScheduleBinding> {
    private OnServiceScheduleListener mScheduleListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_service_schedule;
    }

    @Override
    protected void initView() {

        mBinding.layoutScheduleTime.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                showScheduleTime();
            }
        });
        mBinding.tvTime.setText(DateUtils.timeToString(System.currentTimeMillis(), DateUtils.DATE_PATTERN_2));
        mBinding.tvCancel.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String time = mBinding.tvTime.getText().toString().trim();
                if (TextUtils.isEmpty(time)) {
                    AppUtils.showMessage("请选择上门时间");
                    return;
                }
                String name = mBinding.etUserName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    AppUtils.showMessage("请填写联系人");
                    return;
                }
                String tel = mBinding.etUserTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    AppUtils.showMessage("请填写联系人电话");
                    return;
                }
                String remake = mBinding.etRemake.getText().toString().trim();
                if (mScheduleListener != null) {
                    mScheduleListener.onSure(time, name, tel, remake);
                }
                dismiss();
            }
        });
    }

    private void showScheduleTime() {

        String date = mBinding.tvTime.getText().toString();
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, (date1, v) -> {//选中事件回调
            mBinding.tvTime.setText(DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_2));
        })
                .setCancelColor(AppUtils.getColor(R.color.color_333333))
                .setSubmitColor(AppUtils.getColor(R.color.color_333333))
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true)
                .build();
        Dialog dialog = pvTime.getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.BottomToTop);
            }
        }
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_2));
        }
        pvTime.setDate(calendar);
        pvTime.show();
    }

    public void setOnServiceScheduleListener(OnServiceScheduleListener scheduleListener) {
        mScheduleListener = scheduleListener;
    }

    public interface OnServiceScheduleListener {
        void onSure(String time, String name, String tel, String remake);
    }
}
