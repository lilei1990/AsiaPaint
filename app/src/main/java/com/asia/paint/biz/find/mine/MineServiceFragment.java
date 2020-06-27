package com.asia.paint.biz.find.mine;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.databinding.FragmentMineServiceBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class MineServiceFragment extends BaseFragment<FragmentMineServiceBinding> {
//    private static final int INDEX_SCHEDULE = 0;
    private static final int INDEX_POST = 0;
    private static final int INDEX_FOLLOW = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_service;
    }

    @Override
    protected void initView() {
        mBinding.viewPager.setAdapter(new MineServiceAdapter(getChildFragmentManager()));
//        mBinding.tvServiceSchedule.setOnClickListener(new OnNoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View view) {
//                mBinding.viewPager.setCurrentItem(INDEX_SCHEDULE);
//            }
//        });
        mBinding.tvServicePost.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.viewPager.setCurrentItem(INDEX_POST);
            }
        });
        mBinding.tvServiceFollow.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.viewPager.setCurrentItem(INDEX_FOLLOW);

            }
        });
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTitleColor();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateTitleColor();
    }

    private void updateTitleColor() {
//        mBinding.tvServiceSchedule.setSelected(mBinding.viewPager.getCurrentItem() == INDEX_SCHEDULE);
        mBinding.tvServicePost.setSelected(mBinding.viewPager.getCurrentItem() == INDEX_POST);
        mBinding.tvServiceFollow.setSelected(mBinding.viewPager.getCurrentItem() == INDEX_FOLLOW);
    }

    public class MineServiceAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Fragment> fragments;

        {
            fragments = new SparseArray<>();
//            fragments.append(INDEX_SCHEDULE, new ScheduleFragment());
            fragments.append(INDEX_POST, PostMineFragment.createInstance(PostMineFragment.TYPE_MY_POST));
            fragments.append(INDEX_FOLLOW, PostMineFragment.createInstance(PostMineFragment.TYPE_FOLLOW_POST));

        }

        public MineServiceAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
