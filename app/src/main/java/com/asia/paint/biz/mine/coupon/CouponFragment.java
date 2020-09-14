package com.asia.paint.biz.mine.coupon;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentCouponBinding;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.api.CouponService;
import com.asia.paint.base.network.bean.Coupon;
import com.asia.paint.base.network.bean.CouponRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.main.MainActivity;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class CouponFragment extends BaseFragment<FragmentCouponBinding> {

    private static final String KEY_COUPON_TYPE = "KEY_COUPON_TYPE";
    private CouponAdapter mCouponAdapter;
    private CouponViewModel mViewModel;
    private int mType;

    public static CouponFragment createInstance(int type) {
        CouponFragment fragment = new CouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_COUPON_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_COUPON_TYPE);
    }

    @Override
    protected void initView() {
        mViewModel = getViewModel(CouponViewModel.class);
        mViewModel.resetPage();
        mBinding.rvCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvCoupon.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 16));
        mCouponAdapter = new CouponAdapter(mType);
        mCouponAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadCoupon();
        }, mBinding.rvCoupon);
        mCouponAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Coupon coupon = mCouponAdapter.getData(position);
            if (coupon == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.btn_get) {
                mViewModel.getCoupon(coupon.type_id).setCallback(result -> mCouponAdapter.remove(position));
            }
        });
        mBinding.rvCoupon.setAdapter(mCouponAdapter);
        mCouponAdapter.setOnCouponListener(new CouponAdapter.OnCouponListener() {
            @Override
            public void onSelected(Coupon coupon) {

            }

            @Override
            public void use(Coupon coupon) {
                MainActivity.start(getContext(), MainActivity.Tab.SHOP.getValue());
            }
        });
        loadCoupon();
    }

    public void reset() {
        if (mViewModel == null) {
            return;
        }
        mViewModel.resetPage();
        loadCoupon();
    }

    private void loadCoupon() {

        if (mType == CouponService.TYPE_GET) {
            mViewModel.loadCoupon(null).setCallback(this::updateCoupon);
        } else {
            mViewModel.queryCoupon(mType).setCallback(this::updateCoupon);
        }
    }

    private void updateCoupon(CouponRsp couponRsp) {
        mViewModel.updateListData(mCouponAdapter, couponRsp);
    }

}
