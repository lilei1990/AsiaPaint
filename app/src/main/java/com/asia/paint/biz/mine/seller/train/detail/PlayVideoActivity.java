package com.asia.paint.biz.mine.seller.train.detail;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.databinding.ActivityPlayVideoBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * 播放视频
 */
public class PlayVideoActivity extends BaseActivity<ActivityPlayVideoBinding> {

    private static final String KEY_VIDEO_URL = "KEY_VIDEO_URL";

    private String mUrl;

    public static void start(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(context, PlayVideoActivity.class);
        intent.putExtra(KEY_VIDEO_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mUrl = intent.getStringExtra(KEY_VIDEO_URL);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //地址转url
        Uri uri = Uri.parse(mUrl);
        //显示进度条、播放暂停按钮
        MediaController mediaController = new MediaController(this);
        mBinding.videoView.setMediaController(mediaController);
        //设置视频播放的uri
        mBinding.videoView.setVideoURI(uri);
        //播放
        mBinding.videoView.start();

        //播放完成监听
        mBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束后的动作
                mBinding.ivPlay.setVisibility(View.VISIBLE);
            }
        });
        //关闭按钮
        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        //播放
        mBinding.ivPlay.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                //隐藏播放按钮
                mBinding.ivPlay.setVisibility(View.GONE);
                //重播
                mBinding.videoView.resume();
                mBinding.videoView.start();
            }
        });
    }
}
