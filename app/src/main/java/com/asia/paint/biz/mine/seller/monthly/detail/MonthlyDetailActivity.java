package com.asia.paint.biz.mine.seller.monthly.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.MonthlyDetail;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.mine.seller.monthly.MonthlyViewModel;
import com.asia.paint.databinding.ActivityMonthlyDetailBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-01-03.
 */
public class MonthlyDetailActivity extends BaseActivity<ActivityMonthlyDetailBinding> {


    private static final String KEY_MONTHLY_ID = "KEY_MONTHLY_ID";
    private int mMonthlyId;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, MonthlyDetailActivity.class);
        intent.putExtra(KEY_MONTHLY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mMonthlyId = intent.getIntExtra(KEY_MONTHLY_ID, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_monthly_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonthlyViewModel viewModel = getViewModel(MonthlyViewModel.class);
        mBinding.icBack.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mBinding.wvView.setWebViewClient(new WebViewClient());
        mBinding.wvView.setBackgroundColor(AppUtils.getColor(R.color.color_EDEDED));
        WebSettings webSettings = mBinding.wvView.getSettings();
        //不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        viewModel.queryTrainDetail(mMonthlyId).setCallback(this::updateData);
    }

    private void updateData(MonthlyDetail monthlyDetail) {
        if (monthlyDetail != null) {
            ImageUtils.load(mBinding.ivIcon, monthlyDetail.image);
            mBinding.tvTitle.setText(monthlyDetail.name);
            mBinding.tvTimes.setText(String.format("发布时间：%s", monthlyDetail.add_time));
            String content = monthlyDetail.content;
            if (!TextUtils.isEmpty(content)) {
                //图片适应手机屏幕
                content = content.replace("<img", "<img style=\"display: block;max-width:100%;\"");
            }
            mBinding.wvView.loadData(content, "text/html", "UTF-8");
        }
    }
}
