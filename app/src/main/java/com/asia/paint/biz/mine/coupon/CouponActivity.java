package com.asia.paint.biz.mine.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityCouponBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.CouponService;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class CouponActivity extends BaseActivity<ActivityCouponBinding> {

    private static final String[] mPageTitles = new String[]{"未使用", "已失效"};
    private static final int[] mPageType = new int[]{CouponService.TYPE_USABLE, CouponService.TYPE_OVERDUE};
    private CouponPagerAdapter mCouponPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "优惠券";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding.tvRightText.setText("领券中心");
        mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(CouponActivity.this, CouponCenterActivity.class));
            }
        });
        mCouponPagerAdapter = new CouponPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mCouponPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    public class CouponPagerAdapter extends FragmentStatePagerAdapter {

        public CouponPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return CouponFragment.createInstance(mPageType[position]);
        }

        @Override
        public int getCount() {
            return mPageTitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mPageTitles[position];
        }

        public void reset(int position) {
            CouponFragment fragment = (CouponFragment) getItem(position);
            fragment.reset();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mCouponPagerAdapter != null) {
            mCouponPagerAdapter.reset(mBinding.viewPager.getCurrentItem());
        }
    }
}
