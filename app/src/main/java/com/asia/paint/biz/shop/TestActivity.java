package com.asia.paint.biz.shop;

import android.os.Bundle;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityTestBinding;
import com.asia.paint.base.container.BaseActivity;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-07.
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  /*      String url = "https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";
        String url1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
     //   mBinding.videoNet.setVideoURI(Uri.parse(url1));
        mBinding.videoNet.setVideoPath(url);
        mBinding.btnPlay.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.videoNet.start();
            }
        });

        mBinding.btnStop.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.videoNet.pause();
            }
        });
     *//*   String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        mBinding.player.setUp(url1, true, "测试视频");*/
        mBinding.tvRatingBar.setMax(3);
        mBinding.tvRatingBar.setIsIndicator(true);
        mBinding.tvRatingBar.setNumStars(5);
        mBinding.tvRatingBar.setRating(8f);

    }
}
