package com.asia.paint.base.widgets.show;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewGoodsShowPanelBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author by chenhong14 on 2019-12-14.
 */
public class GoodsShowPanel extends BaseFrameLayout<ViewGoodsShowPanelBinding> {

    private GoodsShowPagerAdapter mGoodsShowPagerAdapter;

    public GoodsShowPanel(@NonNull Context context) {
        super(context);
    }

    public GoodsShowPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsShowPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        FragmentActivity activity = (FragmentActivity) mContext;
        mGoodsShowPagerAdapter = new GoodsShowPagerAdapter(activity.getSupportFragmentManager());
        mBinding.viewPagerShop.setAdapter(mGoodsShowPagerAdapter);
        mBinding.viewPagerShop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_goods_show_panel;
    }

    public class GoodsShowPagerAdapter extends FragmentStatePagerAdapter {

        private List<Integer> data = new ArrayList<>();
        private int mCateId;

        GoodsShowPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return GoodsShowFragment.createInstance(data.get(position), mCateId);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        void updatePage(int page, int cateId) {
            mCateId = cateId;
            data.clear();
            for (int i = 1; i <= page; i++) {
                data.add(i);
            }
            showIndicator(page);
            selectedIndicator(0);
            notifyDataSetChanged();
        }
    }

    public void updateCateId(int cateId) {
        GoodsShowViewModel goodsViewModel = new GoodsShowViewModel();
        goodsViewModel.loadShowGoods(1, cateId).setCallback(result ->
                mGoodsShowPagerAdapter.updatePage(result.totalpage, cateId));
    }

    private TextView createIndicator() {
        TextView tvIndicator = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppUtils.dp2px(6), AppUtils.dp2px(6));
        params.setMargins(AppUtils.dp2px(2), 0, AppUtils.dp2px(2), 0);
        tvIndicator.setLayoutParams(params);
        tvIndicator.setBackgroundResource(R.drawable.bg_indicator_selector);
        return tvIndicator;
    }

    private void showIndicator(int count) {
        mBinding.layoutIndicator.removeAllViews();
        for (int i = 0; i < count; i++) {
            mBinding.layoutIndicator.addView(createIndicator());
        }
    }

    private void selectedIndicator(int position) {
        int childCount = mBinding.layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mBinding.layoutIndicator.getChildAt(i);
            if (child != null) {
                child.setSelected(i == position);
            }
        }
    }
}
