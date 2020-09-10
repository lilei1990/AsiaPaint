package com.asia.paint.biz.order.checkout;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogOrderCouponBinding;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.base.network.bean.Coupon;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.mine.coupon.CouponAdapter;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.AppUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class OrderCouponDialog extends BaseBottomDialogFragment<DialogOrderCouponBinding> {


    private OnChangeCallback<Coupon> mCallback;
    private CouponAdapter mCouponAdapter;
    private List<Coupon> mCoupons;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_order_coupon;
    }

    @Override
    protected void initView() {

        mBinding.rvOrderCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvOrderCoupon.addItemDecoration(new DefaultItemDecoration(0, 8, 0, 0));
        mCouponAdapter = new CouponAdapter(CouponAdapter.TYPE_ORDER);
        mBinding.rvOrderCoupon.setAdapter(mCouponAdapter);
        mCouponAdapter.setOnCouponListener(new CouponAdapter.OnCouponListener() {
            @Override
            public void onSelected(Coupon coupon) {
                mCouponAdapter.singleSelected(coupon);
            }

            @Override
            public void use(Coupon coupon) {

            }
        });
        mBinding.ivClose.setOnClickListener(v -> dismiss());
        mBinding.tvSure.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onChange(mCouponAdapter.getSelectedCoupon());
            }
        });
        setCoupons(mCoupons);
    }

    public void setCoupons(List<Coupon> coupons) {
        mCoupons = coupons;
        if (mCouponAdapter != null) {
            mCouponAdapter.replaceData(coupons);
        }
    }

    public void setCallback(OnChangeCallback<Coupon> callback) {
        mCallback = callback;
    }

    @Override
    protected int getDialogHeight() {
        return AppUtils.dp2px(364);
    }
}
