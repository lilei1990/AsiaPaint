package com.asia.paint.biz.mine.seller.store;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogPintuanBinding;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.base.network.bean.PromotionGroupPintuan;
import com.asia.paint.biz.shop.detail.PinTuanAdapter;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 拼团弹框
 *
 * @author tangkun
 */
public class PinTuanDialog extends BaseDialogFragment<DialogPintuanBinding> {

	private static final String KEY_DATA_LIST = "KEY_DATA_LIST";
	private OnChangeCallback<String> mChangeCallback;
	/**
	 * 拼团列表
	 */
	private List<PromotionGroupPintuan> mPinTuanList = new ArrayList<>();
	/**
	 * 拼团列表适配器
	 */
	private PinTuanAdapter mPinTuanAdapter = null;

	public static PinTuanDialog createInstance(List<PromotionGroupPintuan> pinTuanList) {
		PinTuanDialog dialog = new PinTuanDialog();
		Bundle bundle = new Bundle();
		bundle.putSerializable(KEY_DATA_LIST, (Serializable) pinTuanList);
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
	protected void argumentsValue(Bundle bundle) {
		mPinTuanList = (List<PromotionGroupPintuan>) bundle.getSerializable(KEY_DATA_LIST);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.dialog_pintuan;
	}

	@Override
	protected void initView() {
		mBinding.rvPintuan.setLayoutManager(new LinearLayoutManager(mContext));
		mPinTuanAdapter = new PinTuanAdapter();
		mBinding.rvPintuan.setAdapter(mPinTuanAdapter);
		mPinTuanAdapter.updateData(mPinTuanList);
		//拼团列表去拼单按钮点击事件
		mPinTuanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			if (view.getId() == R.id.tv_join) {
				dismiss();
				if (mChangeCallback != null) {
					mChangeCallback.onChange(String.valueOf(mPinTuanList.get(position).log_id));
				}
			}
		});
		mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				dismiss();
			}
		});
	}

	/**
	 * 刷新弹框列表
	 */
	public void updateList(List<PromotionGroupPintuan> pinTuanList) {
		this.mPinTuanList = pinTuanList;
		mPinTuanAdapter.notifyDataSetChanged();
	}

	@Override
	protected int getDialogWidth() {
		return AppUtils.dp2px(288);
	}

	public void setChangeCallback(OnChangeCallback<String> changeCallback) {
		mChangeCallback = changeCallback;
	}
}
