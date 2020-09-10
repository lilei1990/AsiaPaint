package com.asia.paint.biz.mine.seller.goals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityWebBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.model.OtherViewModel;
import com.asia.paint.base.network.bean.RichTextRsp;

import androidx.annotation.Nullable;

public class WebActivity extends BaseTitleActivity<ActivityWebBinding> {
    private static final String KEY_TITLE = "KEY_TITLE";

    private String mTitle;

    @Override
    protected String middleTitle() {
        return "";
    }

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mTitle = intent.getStringExtra(KEY_TITLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding.tvTitle.setText(mTitle);
        OtherViewModel otherViewModel = getViewModel(OtherViewModel.class);
        mBinding.wvContent.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mBinding.wvContent.getSettings();
        //不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        otherViewModel.loadRichText(mTitle).setCallback(this::updateContent);

    }

    private void updateContent(RichTextRsp richTextRsp) {

        if (richTextRsp == null) {
            return;
        }
        mBaseBinding.tvTitle.setText(richTextRsp.title);
        String content = richTextRsp.content;
        if (!TextUtils.isEmpty(content)) {
            //图片适应手机屏幕
            content = content.replace("<img", "<img style=\"display: block;max-width:100%;\"");
            mBinding.wvContent.loadData(content, "text/html", "UTF-8");
        }
    }
}
