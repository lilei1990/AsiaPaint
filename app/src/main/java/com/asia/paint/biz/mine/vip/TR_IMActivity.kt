package com.asia.paint.biz.mine.vip

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.asia.paint.android.R
import kotlinx.android.synthetic.main.activity_t_r__i_m.*

class TR_IMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_r__i_m)
        mWebView.settings.javaScriptEnabled=true



        //支持javascript
        mWebView.settings.javaScriptEnabled=true
        // 设置可以支持缩放
        mWebView.settings.supportZoom()
        // 设置出现缩放工具
        mWebView.settings.builtInZoomControls=true
        //扩大比例的缩放
        mWebView.settings.useWideViewPort=true
        //自适应屏幕
        mWebView.settings.layoutAlgorithm=WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        //方式一：加载assets文件夹下的test.html页面
        mWebView.loadUrl("https://webchat-bj.clink.cn/chat.html?accessId=adf73a72-0c36-45f1-9eb6-fb3e8b521238&language=zh_CN")
    }
}