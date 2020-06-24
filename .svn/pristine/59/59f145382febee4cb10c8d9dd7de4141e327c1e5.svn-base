package com.asia.paint.base.widgets;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.asia.paint.R;
import com.asia.paint.utils.utils.KeyBoardUtils;


/**
 * 发消息弹框
 */
public class SendMessagePopupWindow {
	private Context mContext;
	//PopupWindow对象
	private PopupWindow mPopupWindow;
	//点击事件
	private OnPopupWindowClickListener onClickListener;

	/**
	 * 一个参数的构造方法，用于弹出无标题的PopupWindow
	 *
	 * @param context
	 */
	public SendMessagePopupWindow(Context context) {
		this.mContext = context;
	}

	/**
	 * 设置选项的点击事件
	 *
	 * @param onClickListener
	 */
	public void setOnClickListener(OnPopupWindowClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	/**
	 * 弹出Popupwindow
	 */
	public void showPopupWindow() {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_bottom_send_message, null);
		initView(contentView);
		KeyBoardUtils.openKeybord(contentView.findViewById(R.id.et_comment));
		mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setAnimationStyle(R.style.popwindowBottomAnimStyle);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				setWindowAlpa(false);
			}
		});
		show(contentView);
	}

	private void initView(View contentView) {
		EditText editText = contentView.findViewById(R.id.et_comment);
		contentView.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				if (onClickListener != null && !TextUtils.isEmpty(editText.getText().toString())) {
					onClickListener.onSendMessageClick(editText.getText().toString());
					editText.setText("");
				}
			}
		});
	}

	/**
	 * 显示PopupWindow
	 */
	private void show(View v) {
		if (mPopupWindow != null && !mPopupWindow.isShowing()) {
			mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		}
		setWindowAlpa(true);
	}


	/**
	 * 消失PopupWindow
	 */
	public void dismiss() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * 动态设置Activity背景透明度
	 *
	 * @param isopen
	 */
	public void setWindowAlpa(boolean isopen) {
		if (Build.VERSION.SDK_INT < 11) {
			return;
		}
		final Window window = ((Activity) mContext).getWindow();
		final WindowManager.LayoutParams lp;
		lp = window.getAttributes();
		window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		ValueAnimator animator;
		if (isopen) {
			animator = ValueAnimator.ofFloat(1.0f, 0.5f);
		} else {
			animator = ValueAnimator.ofFloat(0.5f, 1.0f);
		}
		animator.setDuration(400);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float alpha = (float) animation.getAnimatedValue();
				lp.alpha = alpha;
				window.setAttributes(lp);
			}
		});
		animator.start();
	}

	/**
	 * 点击事件选择回调
	 */
	public interface OnPopupWindowClickListener {
		void onSendMessageClick(String content);
	}
}


