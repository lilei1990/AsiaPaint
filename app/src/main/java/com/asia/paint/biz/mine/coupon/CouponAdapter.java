package com.asia.paint.biz.mine.coupon;

import android.text.TextUtils;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.network.api.CouponService;
import com.asia.paint.base.network.bean.Coupon;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.DigitUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class CouponAdapter extends BaseGlideAdapter<Coupon> {

    public static final int TYPE_ORDER = 999;

    private int mType;

    private List<Coupon> mCoupons = new ArrayList<>();
    private OnCouponListener mListener;

    public CouponAdapter(int type) {
        super(R.layout.item_coupon);
        mType = type;
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Coupon coupon) {
        if (coupon != null) {
            helper.setText(R.id.tv_coupon_value, coupon.money);
            helper.setText(R.id.tv_coupon_name, coupon.name);
            String limitMoney = DigitUtils.parseFloat(coupon.min_money) > 0 ? String.format("满%s可用", coupon.min_money) : "";
            helper.setText(R.id.tv_limit_money, limitMoney);
            if (coupon.type == 2 && !TextUtils.isEmpty(coupon.strtime) && !TextUtils.isEmpty(coupon.endtime)) {
                helper.setText(R.id.tv_coupon_period, String.format("%s-%s", coupon.strtime, coupon.endtime));
            }
            helper.setText(R.id.tv_coupon_limit, coupon.group == 1 ? "全部商品可用" : "部分商品可用");
            switch (mType) {
                case TYPE_ORDER:
                    helper.setGone(R.id.btn_use, false);
                    helper.setGone(R.id.btn_get, false);
                    helper.setGone(R.id.cb_selected, true);
                    break;
                case CouponService
                        .TYPE_OVERDUE:
                    helper.setGone(R.id.btn_use, false);
                    helper.setGone(R.id.btn_get, false);
                    helper.setGone(R.id.cb_selected, false);
                    break;
                case CouponService.TYPE_USABLE:
                    helper.setGone(R.id.btn_use, true);
                    helper.setGone(R.id.btn_get, false);
                    helper.setGone(R.id.cb_selected, false);
                    break;
                case CouponService.TYPE_GET:
                    helper.setGone(R.id.btn_use, false);
                    helper.setGone(R.id.btn_get, true);
                    helper.setGone(R.id.cb_selected, false);
                    break;
            }
            helper.addOnClickListener(R.id.btn_get);
            helper.setOnClickListener(R.id.btn_use, new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    if (mListener != null) {
                        mListener.use(coupon);
                    }
                }
            });
            CheckBox checkBox = helper.getView(R.id.cb_selected);
            checkBox.setChecked(mCoupons.contains(coupon));
            checkBox.setListener(new CheckBox.OnDefaultCheckChangeListener() {
                @Override
                public void onChange(boolean isChecked) {
                    if (isChecked && mListener != null) {
                        mListener.onSelected(coupon);
                    }
                }
            });
        }
    }

    public void singleSelected(Coupon coupon) {
        mCoupons.clear();
        mCoupons.add(coupon);
        notifyDataSetChanged();
    }

    public void selected(Coupon coupon) {
        mCoupons.add(coupon);
        notifyDataSetChanged();
    }

    public List<Coupon> getSelectedCoupons() {

        return mCoupons;
    }


    public Coupon getSelectedCoupon() {
        if (mCoupons.isEmpty()) {
            return null;
        }
        return mCoupons.get(0);
    }

    public void setOnCouponListener(OnCouponListener listener) {
        mListener = listener;
    }

    public interface OnCouponListener {

        void onSelected(Coupon coupon);

        void use(Coupon coupon);
    }
}
