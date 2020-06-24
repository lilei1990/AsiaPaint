package com.asia.paint.biz.find;

import android.util.Pair;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.biz.find.mine.MineServiceFragment;
import com.asia.paint.biz.find.post.PostFragment;
import com.asia.paint.biz.zone.ZoneFragment;
import com.asia.paint.databinding.FragmentFindBinding;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class FindFragment extends BaseFragment<FragmentFindBinding> {

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_find;
	}

	@Override
	protected void initView() {
		mBinding.viewPager.setAdapter(new FindPagerAdapter(getChildFragmentManager()));
		mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
       /* //TODO fixme
        CrashReport.testJavaCrash();*/
	}

	public class FindPagerAdapter extends FragmentStatePagerAdapter {

		private SparseArray<Pair<String, Fragment>> data;

		{
			data = new SparseArray<>();
			data.append(0, new Pair<>("我要选色", new ZoneFragment()));
			data.append(1, new Pair<>("买家秀", PostFragment.createInstance(PostFragment.TYPE_ALL_POST)));
			data.append(2, new Pair<>("我的", new MineServiceFragment()));

		}

		public FindPagerAdapter(@NonNull FragmentManager fm) {
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


