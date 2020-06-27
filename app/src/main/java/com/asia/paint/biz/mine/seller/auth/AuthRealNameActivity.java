package com.asia.paint.biz.mine.seller.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.biz.find.post.publish.PublishPostAdapter;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.biz.mine.money.DistributionTasksActivity;
import com.asia.paint.databinding.ActivityAuthRealNameBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * @author by chenhong14 on 2019-11-14.
 */
public class AuthRealNameActivity extends BaseActivity<ActivityAuthRealNameBinding> {
	public static final int REQUEST_CODE_APPLY = 0xAAAA;
	private MineViewModel mMineViewModel;
	private PublishPostAdapter mPublishPostAdapter;
	private ArrayAdapter<String> adapterRole;
	/**
	 * 地址选择器
	 */
	private AddressPicker addressPicker;
	/**
	 * 省名称
	 */
	private String provinceName = "";
	/**
	 * 市名称
	 */
	private String cityName = "";
	/**
	 * 县名称
	 */
	private String countyName = "";
	/**
	 * 省编码
	 */
	private String provinceCode = "";
	/**
	 * 市编码
	 */
	private String cityCode = "";
	/**
	 * 县编码
	 */
	private String countyCode = "";

	public static void start(Activity activity) {
		Intent intent = new Intent(activity, AuthRealNameActivity.class);
		activity.startActivityForResult(intent, REQUEST_CODE_APPLY);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_auth_real_name;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "实名认证";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMineViewModel = getViewModel(MineViewModel.class);
		mBinding.rvIdCardPhoto.setLayoutManager(new GridLayoutManager(this, 3));
		mBinding.rvIdCardPhoto.setItemAnimator(null);
		mPublishPostAdapter = new PublishPostAdapter();
		mBinding.rvIdCardPhoto.setAdapter(mPublishPostAdapter);
		mPublishPostAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			int id = view.getId();
			if (id == R.id.iv_delete) {
				mPublishPostAdapter.getData().remove(mPublishPostAdapter.getData(position));
				addImageUrls(new ArrayList<>());
				mPublishPostAdapter.notifyDataSetChanged();
			} else if (id == R.id.iv_img && mPublishPostAdapter.isAddImg(view.getTag())) {
				MatisseActivity.start(AuthRealNameActivity.this, this::addImageUrls);
			}
		});
		mPublishPostAdapter.addImg(new ArrayList<>());
		mBinding.btnAuth.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startAuth();
			}
		});
		mPublishPostAdapter.addImg(new ArrayList<>());

		//所在区域
		mBinding.tvArea.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				showAddressPicker();
			}
		});
		//复选框
		mBinding.tvSmz.setText(CacheUtils.getString(CacheUtils.KEY_SMZ) + "");
		initData();
	}

	private void showAddressPicker() {
		if (addressPicker != null && addressPicker.isShowing()) {
			addressPicker.dismiss();
		} else {
			ArrayList<Province> data = new ArrayList<Province>();
			try {
				String json = ConvertUtils.toString(getAssets().open("city.json"));
				data.addAll(JSON.parseArray(json, Province.class));
				addressPicker = new AddressPicker(AuthRealNameActivity.this, data);
				//顶部高度
				addressPicker.setTopHeight(47);
				//取消按钮颜色
				addressPicker.setCancelTextColor(getResources().getColor(R.color.color_2F54CC));
				//取消按钮文字大小
				addressPicker.setCancelTextSize(17);
				//确认按钮颜色
				addressPicker.setSubmitTextColor(getResources().getColor(R.color.color_2F54CC));
				//按下的文字颜色
				addressPicker.setPressedTextColor(getResources().getColor(R.color.color_2F54CC));
				//确认按钮文字大小
				addressPicker.setSubmitTextSize(17);
				//顶部标题栏下划线颜色
				addressPicker.setTopLineColor(getResources().getColor(R.color.color_F8F8F8));
				//顶部标题栏下划线高度
				addressPicker.setTopLineHeight(1);
				//日期上下分割线颜色
				addressPicker.setLineColor(getResources().getColor(R.color.color_F8F8F8));
				//日期颜色
				addressPicker.setTextColor(getResources().getColor(R.color.color_333333));
				//控件展示动画
				addressPicker.setAnimationStyle(R.style.PopupwindowStyle);
				//仅显示省和市,隐藏区
				addressPicker.setHideCounty(true);
				//历史选中省市县
				if (!TextUtils.isEmpty(provinceName) && !TextUtils.isEmpty(cityName)) {
					addressPicker.setSelectedItem(provinceName, cityName, countyName);
				}
				addressPicker.setOnAddressPickListener((province, city, county) -> {
					provinceName = province.getAreaName();
					cityName = city.getAreaName();
					provinceCode = province.getAreaId();
					cityCode = city.getAreaId();
//						countyName = county.getAreaName();
//						countyCode = county.getAreaId();
					mBinding.tvArea.setText(provinceName + cityName);
				});
				addressPicker.show();
			} catch (Exception e) {

			}
		}
	}

	private void initData() {
		//h5内容
		mMineViewModel.loadArticleData().setCallback(result -> {
					//接口返回的h5展示内容
					String content = result.content;
					mBinding.mWebView.loadData(content, "text/html", "UTF-8");
					mBinding.mWebView.setWebViewClient(new WebViewClient());
					mBinding.mWebView.setBackgroundColor(Color.TRANSPARENT);
					WebSettings webSettings = mBinding.mWebView.getSettings();
					//不支持屏幕缩放
					webSettings.setSupportZoom(false);
					webSettings.setBuiltInZoomControls(false);
					//不显示webview缩放按钮
					webSettings.setDisplayZoomControls(false);
				}
		);

		//职位列表
		mMineViewModel.loadGetuserpost().setCallback(result -> {
			if (result != null) {
				String array[] = new String[result.size()];
				for (int i = 0; i < result.size(); i++) {
					array[i] = result.get(i).name;
				}
				adapterRole = new ArrayAdapter(this, R.layout.view_my_spinner, array);
				adapterRole.setDropDownViewResource(R.layout.view_my_spinner);
				mBinding.spinnerRole.setAdapter(adapterRole);
				mBinding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			}
		});
	}

	private void startAuth() {
		String name = getText(mBinding.etRealName);
		if (TextUtils.isEmpty(name)) {
			AppUtils.showMessage("请输入姓名");
			return;
		}
		String post = mBinding.spinnerRole.getSelectedItem().toString();
		if (TextUtils.isEmpty(post)) {
			AppUtils.showMessage("请选择职位");
			return;
		}
		String id = getText(mBinding.etId);
		if (TextUtils.isEmpty(id)) {
			AppUtils.showMessage("请输入身份证");
			return;
		}
		String address = getText(mBinding.tvArea);
		if (TextUtils.isEmpty(address)) {
			AppUtils.showMessage("请选择所在区域");
			return;
		}
		requestAuth(address, address, id, name, post);
	}

	private void requestAuth(String address, String address_name, String idCard, String name, String post) {
		mMineViewModel.applySeller(address, address_name, idCard, name, post).setCallback(result1 -> {
			if (result1) {
				AppUtils.showMessage("认证成功");
				setResult(RESULT_OK);
				DistributionTasksActivity.start(AuthRealNameActivity.this);
				finish();
			} else {
				authFail();
			}
		});
	}


	private void authFail() {
		MessageDialog dialog = new MessageDialog.Builder()
				.title("认证失败")
				.message("请填写正确的姓名和身份证号")
				.setSureButton("知道了", null)
				.build();
		dialog.show(this);
	}


	private void addImageUrls(List<String> result) {
		mPublishPostAdapter.addImg(result, 2);
	}
}
