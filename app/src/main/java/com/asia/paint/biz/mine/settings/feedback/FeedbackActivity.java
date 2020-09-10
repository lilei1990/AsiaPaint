package com.asia.paint.biz.mine.settings.feedback;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityFeedbackBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.KeyBoardUtils;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding> {

    private FeedbackViewModel mViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "意见反馈";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(FeedbackViewModel.class);
        mBaseBinding.tvRightText.setText("提交");
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String content = mBinding.etFeedback.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    AppUtils.showMessage("意见与建议不能为空");
                    return;
                }
                mViewModel.submitFeedback(content);
                KeyBoardUtils.closeSoftInput(FeedbackActivity.this);
                finish();
            }
        });

    }
}
