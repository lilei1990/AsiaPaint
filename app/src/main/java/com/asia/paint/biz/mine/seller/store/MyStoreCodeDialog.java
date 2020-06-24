package com.asia.paint.biz.mine.seller.store;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.databinding.DialogStoreCodeBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.QRCode;

/**
 * 我的店铺码
 *
 * @author tangkun
 */
public class MyStoreCodeDialog extends BaseDialogFragment<DialogStoreCodeBinding> {

	private static final String KEY_ASIA_CODE = "KEY_ASIA_CODE";
	private String asiaCode;
	private OnChangeCallback<String> mChangeCallback;

	public static MyStoreCodeDialog createInstance(String asiaCode) {
		MyStoreCodeDialog dialog = new MyStoreCodeDialog();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_ASIA_CODE, asiaCode);
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
	protected void argumentsValue(Bundle bundle) {
		asiaCode = bundle.getString(KEY_ASIA_CODE);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.dialog_store_code;
	}

	@Override
	protected void initView() {

		//分享给别人
		mBinding.tvStoreCodeShare.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				dismiss();
				if (mChangeCallback != null) {
					mChangeCallback.onChange(asiaCode);
				}
			}
		});
		mBinding.ivStoreCodeClose.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				dismiss();
			}
		});
		if (!TextUtils.isEmpty(asiaCode)) {
			String content = Constants.URL + "?sellerId=" + asiaCode;
			Bitmap qrCode = QRCode.createQRCode(content, AppUtils.dp2px(183));
			if (qrCode != null) {
				mBinding.ivQrCode.setImageBitmap(qrCode);
			}
		}

//        if (mChangeCallback != null) {
//            mChangeCallback.onChange(mBinding.viewPwd.getPassword());
//        }
//        dismiss();
	}

	@Override
	protected int getDialogWidth() {
		return AppUtils.dp2px(288);
	}

	public void setChangeCallback(OnChangeCallback<String> changeCallback) {
		mChangeCallback = changeCallback;
	}
}
