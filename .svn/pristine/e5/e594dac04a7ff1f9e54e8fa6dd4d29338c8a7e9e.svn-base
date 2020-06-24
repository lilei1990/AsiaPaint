package com.asia.paint.biz.mine.seller.train;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.TrainCategory;
import com.asia.paint.databinding.ActivityTrainBinding;
import com.asia.paint.utils.callback.OnChangeCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class TrainActivity extends BaseTitleActivity<ActivityTrainBinding> {

    private TrainPagerAdapter mTrainPagerAdapter;
    private TrainViewModel mTrainViewModel;

    @Override
    protected String middleTitle() {
        return "培训中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrainViewModel = getViewModel(TrainViewModel.class);
        mBinding.viewSearch.setChangeCallback(new OnChangeCallback<String>() {
            @Override
            public void onChange(String result) {
                mTrainPagerAdapter.search(mBinding.viewPager.getCurrentItem(), result);
            }
        });
        mTrainPagerAdapter = new TrainPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mTrainPagerAdapter);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.viewSearch.setText("");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mTrainViewModel.loadTrainCategory().setCallback(result -> mTrainPagerAdapter.update(result.categories));
    }

    private class TrainPagerAdapter extends FragmentStatePagerAdapter {

        private List<TrainCategory> mCategory = new ArrayList<>();

        public TrainPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return TrainFragment.createInstance(mCategory.get(position));
        }

        @Override
        public int getCount() {
            return mCategory.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mCategory.get(position).name;
        }

        public void update(List<TrainCategory> categorys) {
            mCategory.clear();
            if (categorys != null) {
                mCategory.addAll(categorys);
            }
            notifyDataSetChanged();
        }

        public void search(int position, String keyword) {
            TrainFragment fragment = (TrainFragment) getItem(position);
            fragment.search(keyword);
        }
    }
}
