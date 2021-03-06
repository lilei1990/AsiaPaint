package com.asia.paint.biz.shop.goods;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-05.
 */
public class GoodsTagAdapter extends BaseGlideAdapter<String> {

    private int mSelectedPosition;
    private GoodsViewModel.Sort mSort;

    public GoodsTagAdapter(@Nullable List<String> data) {
        super(R.layout.item_goods_tag, data);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, String category) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_tag, category);
        updatePriceTag(helper.getView(R.id.tv_tag), position);
        helper.getView(R.id.tv_tag).setSelected(position == mSelectedPosition);
    }

    private void updatePriceTag(TextView view, int position) {
        //暂时不显示价格旁边的图标
//        if (isPriceTag(position)) {
//            Drawable flag = mContext.getDrawable(R.mipmap.ic_price_normal);
//            if (mSort == GoodsViewModel.Sort.DESC) {
//                flag = mContext.getDrawable(R.mipmap.ic_price_desc);
//            } else if (mSort == GoodsViewModel.Sort.ASC) {
//                flag = mContext.getDrawable(R.mipmap.ic_price_asc);
//            }
//            if (flag != null) {
//                // 设置边界，不然图片不显示
//                flag.setBounds(0, 0, flag.getMinimumWidth(),
//                        flag.getMinimumHeight());
//
//                view.setCompoundDrawables(null, null, flag, null);
//                view.setCompoundDrawablePadding(AppUtils.dp2px(3));
//            }
//        }
    }

    public boolean isPriceTag(int position) {
        String tag = getData(position);
        return TextUtils.equals(tag, "价格");
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    public String getCurTag(){
        return getData(mSelectedPosition);
    }

    public void setSort(GoodsViewModel.Sort sort) {
        mSort = sort;
        notifyDataSetChanged();
    }

    public void switchTag() {
        if (mSort == null) {
            return;
        }
        mSort = mSort == GoodsViewModel.Sort.ASC ? GoodsViewModel.Sort.DESC : GoodsViewModel.Sort.ASC;
        notifyDataSetChanged();
    }

    public GoodsViewModel.Sort getSort() {
        return mSort;
    }
}
