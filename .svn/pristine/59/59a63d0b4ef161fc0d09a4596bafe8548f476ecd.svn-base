package com.asia.paint.biz.mine.seller.staff.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.StaffService;
import com.asia.paint.base.network.bean.StaffInfoRsp;
import com.asia.paint.base.network.bean.StaffSaleDataRsp;
import com.asia.paint.base.network.bean.User;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.mine.seller.staff.StaffViewModel;
import com.asia.paint.databinding.ActivityStaffDetailBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-29.
 */
public class StaffDetailActivity extends BaseActivity<ActivityStaffDetailBinding> {

    private static final String KEY_STAFF_TYPE = "KEY_STAFF_TYPE";
    private static final String KEY_STAFF_ID = "KEY_STAFF_ID";
    private int mType;
    private int mStaffId;
    private StaffViewModel mStaffViewModel;
    private StaffDealAdapter mStaffDealAdapter;


    public static void start(Context context, int type, int staffId) {
        Intent intent = new Intent(context, StaffDetailActivity.class);
        intent.putExtra(KEY_STAFF_TYPE, type);
        intent.putExtra(KEY_STAFF_ID, staffId);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mType = intent.getIntExtra(KEY_STAFF_TYPE, StaffService.TYPE_STAFF);
        mStaffId = intent.getIntExtra(KEY_STAFF_ID, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStaffViewModel = getViewModel(StaffViewModel.class);
        mStaffViewModel.resetPage();
        mBinding.icBack.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mBinding.layoutStaff.setVisibility(mType == StaffService.TYPE_STAFF ? View.VISIBLE : View.GONE);
        mBinding.layoutSubStaff.setVisibility(mType == StaffService.TYPE_SUB_STAFF ? View.VISIBLE : View.GONE);
        mBinding.rvStaffDetail.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvStaffDetail.addItemDecoration(new DefaultItemDecoration(14, 0, 14, 8));
        mStaffDealAdapter = new StaffDealAdapter();
        mStaffDealAdapter.setOnLoadMoreListener(() -> {
            mStaffViewModel.autoAddPage();
            queryStaffDetail();
        }, mBinding.rvStaffDetail);
        mBinding.rvStaffDetail.setAdapter(mStaffDealAdapter);

        setAvatar(null);
        queryStaffDetail();
        if (mType == StaffService.TYPE_SUB_STAFF) {
            Calendar yesterday = Calendar.getInstance();
            String endDate = DateUtils.timeToString(yesterday.getTimeInMillis(), DateUtils.DATE_PATTERN_1);
            yesterday.add(Calendar.DATE, -1);
            String startDate = DateUtils.timeToString(yesterday.getTimeInMillis(), DateUtils.DATE_PATTERN_1);
            querySaleData(startDate, endDate);
        }
        mBinding.layoutToday.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                long currentTime = System.currentTimeMillis();
                String date = DateUtils.timeToString(currentTime, DateUtils.DATE_PATTERN_1);
                querySaleData(date, date);
            }
        });
        mBinding.tvStartTime.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                setStartDate();
            }
        });
        mBinding.tvEndTime.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                setEndDate();
            }
        });
    }

    private void queryStaffDetail() {
        mStaffViewModel.queryStaffDetail(mStaffId).setCallback(this::updateStaffInfoRsp);
    }

    private void setStartDate() {

        String date = mBinding.tvStartTime.getText().toString();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选
            // 中事件回调
            String startTime = DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_1);
            mBinding.tvStartTime.setText(startTime);
            String endTime = mBinding.tvEndTime.getText().toString();
            if (!TextUtils.equals(endTime, "结束时间")) {
                querySaleData(startTime, endTime);
            }
        })
                .setCancelColor(AppUtils.getColor(R.color.color_333333))
                .setSubmitColor(AppUtils.getColor(R.color.color_333333))
                .setContentTextSize(18)
                .build();
        if (!TextUtils.equals(date, "开始时间")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_1));
            pvTime.setDate(calendar);
        }
        pvTime.show();
    }

    private void setEndDate() {

        String date = mBinding.tvEndTime.getText().toString();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选中事件回调
            String endTime = DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_1);
            mBinding.tvEndTime.setText(endTime);
            String startTime = mBinding.tvStartTime.getText().toString();
            if (!TextUtils.equals(startTime, "开始时间")) {
                querySaleData(startTime, endTime);
            }

        })
                .setCancelColor(AppUtils.getColor(R.color.color_333333))
                .setSubmitColor(AppUtils.getColor(R.color.color_333333))
                .setContentTextSize(18)
                .build();
        if (!TextUtils.equals(date, "结束时间")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_1));
            pvTime.setDate(calendar);
        }
        pvTime.show();
    }


    private void querySaleData(String startDate, String endDate) {
        mStaffViewModel.queryStaffSaleData(mStaffId, startDate, endDate).setCallback(this::updateSubStaffInfo);
    }

    private void updateStaffInfoRsp(StaffInfoRsp staffInfo) {
        if (staffInfo == null) {
            return;
        }
        updateUserInfo(staffInfo.user);
        updateStaffInfo(staffInfo);
    }

    private void updateUserInfo(User user) {

        setAvatar(user);
        mBinding.tvStaffName.setText(user == null ? "--" : user.nickname);
        mBinding.tvStaffPhone.setText(user == null ? "--" : user.mobile);
    }

    private void setAvatar(User user) {
        ImageUtils.loadCircleImage(mBinding.ivAvatar, user == null ? R.mipmap.ic_default :
                TextUtils.isEmpty(user.avatar) ? R.mipmap.ic_default : user.avatar);
    }

    private void updateStaffInfo(StaffInfoRsp staffInfo) {
        mStaffViewModel.updateListData(mStaffDealAdapter, staffInfo);
    }

    private void updateSubStaffInfo(StaffSaleDataRsp staffInfo) {
        mBinding.tvDealCount.setText(String.valueOf(staffInfo.order));
        mBinding.tvDealAmount.setText(String.valueOf(staffInfo.money));
        mBinding.tvNewStaffCount.setText(String.valueOf(staffInfo.user_count));
    }
}
