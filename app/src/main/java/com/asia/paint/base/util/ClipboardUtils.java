package com.asia.paint.base.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.smarttop.library.utils.LogUtil;

/**
 * @tangkun 剪贴板工具类
 */
public class ClipboardUtils {

	/**
	 * 获取系统剪贴板内容
	 */
	public static String getClipContent(Context context) {
		ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (manager != null) {
			if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
				CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
				String addedTextString = String.valueOf(addedText);
				if (!TextUtils.isEmpty(addedTextString)) {
					return addedTextString;
				}
			}
		}
		return "";
	}

	/**
	 * 清空剪贴板内容
	 */
	public static void clearClipboard(Context context) {
		ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (manager != null) {
			try {
				manager.setPrimaryClip(manager.getPrimaryClip());
				manager.setText(null);
			} catch (Exception e) {
				LogUtil.e("ClipboardUtils clearClipboard Exception:", e.getMessage());
			}
		}
	}
}
