package com.asia.paint.biz.decoration;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityDecorationDetailBinding;
import com.asia.paint.base.container.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DecorationDetailActivity extends BaseActivity<ActivityDecorationDetailBinding> {
	private String id = null;
	private DecorationDetailViewModel mDecorationDetailViewModel;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_decoration_detail;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "家装宝典详情";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id = getIntent().getStringExtra("id");
		mDecorationDetailViewModel = getViewModel(DecorationDetailViewModel.class);
		initData();
	}


	private void initData() {
		//h5内容
		mDecorationDetailViewModel.loadIndexNewsDetail(id).setCallback(result -> {
					mBinding.mWebView.setBackgroundColor(Color.TRANSPARENT);
					//接口返回的h5展示内容
					String content = result.content;
					String htmlContent = changeImgWidth(content);
					mBinding.mWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
					mBinding.mWebView.setWebViewClient(new WebViewClient());
				}
		);
	}

	/**
	 * @param htmlContent 原来的html
	 * @return 改变img标签宽度以后的html
	 */
	public static String changeImgWidth(String htmlContent) {
		Document doc_Dis = Jsoup.parse(htmlContent);
		Elements ele_Img = doc_Dis.getElementsByTag("img");
		if (ele_Img.size() != 0) {
			for (Element e_Img : ele_Img) {
				e_Img.attr("style", "width:100%");
			}
		}
		String newHtmlContent = doc_Dis.toString();
		return newHtmlContent;
	}
}
