package com.asia.paint.biz.mine.seller.staff;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Staff;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * @author by chenhong14 on 2019-12-29.
 */
public class StaffAdapter extends BaseGlideAdapter<Staff> {

	public StaffAdapter() {
		super(R.layout.item_staff);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, Staff staff) {
		if (staff != null) {
			boolean isLetter = !TextUtils.isEmpty(staff.getLetter());
			helper.setGone(R.id.view_top, isLetter);
			helper.setGone(R.id.tv_letter, isLetter);
			helper.setGone(R.id.view_divider_letter, isLetter);
			helper.setText(R.id.tv_letter, staff.getLetter());
			helper.setText(R.id.tv_content, staff.getContent() + "，" + staff.mobile);
			helper.addOnClickListener(R.id.tv_content);
		}
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(String section) {
		for (int i = 0; i < getItemCount(); i++) {
			Staff staff = getData(i);
			if (staff != null && TextUtils.equals(section, staff.getLetter())) {
				return i;
			}
		}
		return -1;
	}
}
