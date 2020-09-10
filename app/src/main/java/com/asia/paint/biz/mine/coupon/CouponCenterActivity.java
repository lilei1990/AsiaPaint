package com.asia.paint.biz.mine.coupon;

import android.content.Intent;
import android.os.Bundle;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityCouponCenterBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.CouponService;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class CouponCenterActivity extends BaseTitleActivity<ActivityCouponCenterBinding> {
    @Override
    protected String middleTitle() {
        return "领券中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_center;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CouponFragment fragment = CouponFragment.createInstance(CouponService.TYPE_GET);
            if (fragment != null) {
                fragmentTransaction.add(R.id.layout_container, fragment);
                fragmentTransaction.commit();
            }
        }
    }
}
