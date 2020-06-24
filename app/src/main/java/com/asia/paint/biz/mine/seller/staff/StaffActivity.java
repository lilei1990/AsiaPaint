package com.asia.paint.biz.mine.seller.staff;

import static com.asia.paint.base.network.api.StaffService.TYPE_STAFF;
import static com.asia.paint.base.network.api.StaffService.TYPE_SUB_STAFF;

import android.os.Bundle;
import android.util.Pair;
import android.util.SparseArray;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.databinding.ActivityStaffBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class StaffActivity extends BaseTitleActivity<ActivityStaffBinding> {


    private StaffFragmentAdapter mStaffFragmentAdapter;

    @Override
    protected String middleTitle() {
        return "我的客户";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStaffFragmentAdapter = new StaffFragmentAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mStaffFragmentAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
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
        mBinding.viewSearch.setChangeCallback(result ->
                mStaffFragmentAdapter.search(mBinding.viewPager.getCurrentItem(), result));
        setStaffCount(0);
    }

    public class StaffFragmentAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Pair<String, Fragment>> data;

        {
            data = new SparseArray<>();
            data.append(0, new Pair<>("直属用户", StaffFragment.createInstance(TYPE_STAFF)));
            data.append(1, new Pair<>("间接客户", StaffFragment.createInstance(TYPE_SUB_STAFF)));
        }

        public StaffFragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return data.get(position).second;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).first;
        }

        public void search(int position, String keyword) {
            try {
                StaffFragment fragment = (StaffFragment) getItem(position);
                fragment.search(keyword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setStaffCount(int count) {
        mBinding.tvStaffCount.setText(String.format("共%s人", count));
    }
}
