package com.asia.paint.biz.mine.seller.task;

import android.os.Bundle;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityTaskCenterBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.TaskService;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class TaskCenterActivity extends BaseTitleActivity<ActivityTaskCenterBinding> {
    @Override
    protected String middleTitle() {
        return "任务中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_center;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding.tvRightText.setText("任务规则");
        mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

            }
        });
        mBinding.viewPager.setAdapter(new TaskCenterPagerAdapter(getSupportFragmentManager()));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

    }

    public class TaskCenterPagerAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Pair<String, Fragment>> data;

        {
            data = new SparseArray<>();
            data.append(0, new Pair<>("全部", TaskCenterFragment.createInstance(TaskService.TASK_ALL)));
            data.append(1, new Pair<>("待完成", TaskCenterFragment.createInstance(TaskService.TASK_TODO)));
            data.append(2, new Pair<>("已完成", TaskCenterFragment.createInstance(TaskService.TASK_DONE)));

        }

        public TaskCenterPagerAdapter(@NonNull FragmentManager fm) {
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
    }
}
