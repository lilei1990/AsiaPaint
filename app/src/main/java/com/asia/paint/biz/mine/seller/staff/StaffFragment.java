package com.asia.paint.biz.mine.seller.staff;

import android.os.Bundle;
import android.text.TextUtils;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Staff;
import com.asia.paint.base.util.CharacterParser;
import com.asia.paint.base.util.PinYinUtils;
import com.asia.paint.biz.mine.seller.staff.detail.StaffDetailActivity;
import com.asia.paint.databinding.FragmentStaffBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author by chenhong14 on 2019-12-29.
 */
public class StaffFragment extends BaseFragment<FragmentStaffBinding> {

    private static final String KEY_STAFF_TYPE = "KEY_STAFF_TYPE";
    private StaffAdapter mStaffAdapter;
    private List<Staff> mStaffList;
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setStaffCount();
        }
    };
    private int mType;

    public static StaffFragment createInstance(int type) {
        StaffFragment fragment = new StaffFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STAFF_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_STAFF_TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_staff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createStaffAdapter();
    }

    @Override
    protected void initView() {
        StaffViewModel staffViewModel = getViewModel(StaffViewModel.class);
        mBinding.rvStaff.setLayoutManager(new LinearLayoutManager(mContext));
        createStaffAdapter();
        mBinding.rvStaff.setAdapter(mStaffAdapter);
        mBinding.sideBar.setOnTouchingLetterChangedListener(letter -> {
            if (mStaffAdapter == null) {
                return;
            }
            // 该字母首次出现的位置
            int position = mStaffAdapter.getPositionForSection(letter);
            if (position != -1) {
                mBinding.rvStaff.scrollToPosition(position);
            }
        });
        staffViewModel.loadStaff(mType).setCallback(result -> {

            mStaffList = result.data;
            sortStaff(mStaffList);
            mStaffAdapter.replaceData(mStaffList);
        });
    }

    private void createStaffAdapter() {
        if (mStaffAdapter == null) {
            mStaffAdapter = new StaffAdapter();
            mStaffAdapter.registerAdapterDataObserver(mDataObserver);
            mStaffAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                Staff staff = mStaffAdapter.getData(position);
                if (staff != null) {
                    StaffDetailActivity.start(mContext, mType, staff.id);
                }
            });
        }
    }

    void search(String keyword) {
        CharacterParser characterParser = new CharacterParser();
        List<Staff> searchList = new ArrayList<>();
        if (mStaffAdapter == null || mStaffList == null || mStaffList.isEmpty()) {
            return;
        }

        if (TextUtils.isEmpty(keyword)) {
            searchList = mStaffList;
        } else {
            for (Staff staff : mStaffList) {
                String name = staff.getContent();
                // 中文和字母搜索
                if (name.contains(keyword)
                        || characterParser.getSelling(name).startsWith(
                        keyword.toLowerCase(Locale.ENGLISH))) {
                    searchList.add(staff);
                }
            }
        }
        mStaffAdapter.replaceData(searchList);
    }

    private int getStaffCount() {
        return mStaffAdapter.getItemCount();
    }

    private void setStaffCount() {
        StaffActivity activity = (StaffActivity) mActivity;
        activity.setStaffCount(getStaffCount());
    }

    private void sortStaff(List<Staff> staffList) {

        if (staffList == null || staffList.isEmpty()) {
            return;
        }
        PinYinUtils.sortLetter(staffList);
        Collections.sort(staffList);
        String letter = "";
        for (Staff staff : staffList) {
            if (staff != null) {
                if (!TextUtils.equals(letter, staff.getLetter())) {
                    letter = staff.getLetter();
                } else {
                    staff.setLetter("");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mStaffAdapter.unregisterAdapterDataObserver(mDataObserver);
        super.onDestroy();
    }
}
