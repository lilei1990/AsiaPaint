package com.asia.paint.base.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.databinding.ViewHostTabBinding;
import com.asia.paint.databinding.ViewTabHostBinding;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class TabHost extends LinearLayout {
    private FragmentTabHost mTabHost;
    ViewTabHostBinding mBinding;

    public TabHost(Context context) {
        this(context, null);
    }

    public TabHost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_tab_host, this, true);
        mTabHost = findViewById(android.R.id.tabhost);
    }

    public void setOnTabChangedListener(android.widget.TabHost.OnTabChangeListener onTabChangedListener) {
        mTabHost.setOnTabChangedListener(onTabChangedListener);
    }

    public void setRedDot(String text, int tabIndex) {
        View hostTab = mTabHost.getTabWidget().getChildTabViewAt(tabIndex);
        TextView redDotTv = hostTab.findViewById(R.id.tv_red_dot);
        redDotTv.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        redDotTv.setText(text);
    }

    private View getHostTabView(HostTab tab) {

        Context context = getContext();
        // 获取每个Tab的view布局
        ViewHostTabBinding hostTab = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_host_tab, null, false);

        // 设置title
        hostTab.tvTab.setText(tab.getTitle());
        // 设置image
        Drawable topDrawable = context.getResources().getDrawable(
                tab.getImage());
        // 设置边界，不然图片不显示
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(),
                topDrawable.getMinimumHeight());
        hostTab.tvTab.setCompoundDrawables(null, topDrawable, null, null);

        return hostTab.getRoot();
    }

    public void setTab(int index) {
        mTabHost.setCurrentTab(index);
    }


    public void setup(FragmentActivity activity, List<HostTab> hostTabs) {
        mTabHost.setup(activity, activity.getSupportFragmentManager(), R.id.realTabContent);
        for (HostTab tab : hostTabs) {
            android.widget.TabHost.TabSpec tabSpec = mTabHost.newTabSpec(activity.getString(tab.getTitle()));
            tabSpec.setIndicator(getHostTabView(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), tab.getBundle());
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    public static class HostTab {
        /**
         * 标题
         */
        private int title;
        /**
         * 图标
         */
        private int image;
        /**
         * tab对应的Fragment
         */
        private Class<?> fragment;
        /**
         * 传递到对应Fragment的数据
         */
        private Bundle bundle;

        /**
         * @param title    标题
         * @param image    图标
         * @param fragment tab对应的fragment
         * @param bundle   传递到fragment的数据
         */
        public HostTab(int title, int image, Class<?> fragment, Bundle bundle) {
            this.title = title;
            this.image = image;
            this.fragment = fragment;
            this.bundle = bundle;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public Class<?> getFragment() {
            return fragment;
        }

        public void setFragment(Class<?> fragment) {
            this.fragment = fragment;
        }

        public Bundle getBundle() {
            return bundle;
        }

        public void setBundle(Bundle bundle) {
            this.bundle = bundle;
        }

    }
}
