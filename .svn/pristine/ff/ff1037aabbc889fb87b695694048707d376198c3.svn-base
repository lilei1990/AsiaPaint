package com.asia.paint.biz.order.mine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.databinding.ActivityOrderMineBinding;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author by chenhong14 on 2019-11-26.
 */
public class OrderMineActivity extends BaseActivity<ActivityOrderMineBinding> {
    private static final String KEY_ORDER_MINE_TYPE = "KEY_ORDER_MINE_TYPE";
    public static final Map<Integer, String> ORDER_TYPE = new LinkedHashMap<>();

    static {
        ORDER_TYPE.put(OrderService.ORDER_ALL, "全部");
        ORDER_TYPE.put(OrderService.ORDER_PAY, "待付款");
        ORDER_TYPE.put(OrderService.ORDER_DELIVERY, "待发货");
        ORDER_TYPE.put(OrderService.ORDER_RECEIVE, "待收货");
        ORDER_TYPE.put(OrderService.ORDER_COMMENT, "待评价");
    }

    private int mType;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, OrderMineActivity.class);
        intent.putExtra(KEY_ORDER_MINE_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mType = intent.getIntExtra(KEY_ORDER_MINE_TYPE, OrderService.ORDER_ALL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_mine;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "我的订单";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.viewPager.setAdapter(new OrderPagerAdapter(getSupportFragmentManager()));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        setCurrentPage();
    }

    private void setCurrentPage() {

        try {
            Integer[] indexPage = ORDER_TYPE.keySet().toArray(new Integer[]{});
            List<Integer> integers = Arrays.asList(indexPage);
            int index = integers.indexOf(mType);
            mBinding.viewPager.setCurrentItem(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class OrderPagerAdapter extends FragmentStatePagerAdapter {

        public OrderPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return OrderFragment.createInstance(ORDER_TYPE.keySet().toArray(new Integer[]{})[position]);
        }

        @Override
        public int getCount() {
            return ORDER_TYPE.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return ORDER_TYPE.values().toArray(new String[]{})[position];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}

