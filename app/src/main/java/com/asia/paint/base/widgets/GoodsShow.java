package com.asia.paint.base.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewGoodsShowBinding;
import com.asia.paint.banner.listener.OnBannerListener;
import com.asia.paint.base.image.GoodsLoader;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author by chenhong14 on 2019-11-07.
 */
public class GoodsShow extends LinearLayout {

    private ViewGoodsShowBinding mBinding;
    private GoodsShowAdapter mGoodsShowAdapter;
    private List<String> mGoods = new ArrayList<>();

    public GoodsShow(Context context) {
        this(context, null);
    }

    public GoodsShow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsShow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_goods_show, this, true);
        init(context);
    }

    private void init(Context context) {
        mBinding.rvGoods.setLayoutManager(new GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false));
        mGoodsShowAdapter = new GoodsShowAdapter(mGoods);
        mBinding.rvGoods.setAdapter(mGoodsShowAdapter);
        mBinding.rvGoods.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space = AppUtils.dp2px(10);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildLayoutPosition(view);
                if (position >= 2) {
                    outRect.set(space, 0, 0, 0);
                }

            }
        });
        mBinding.viewGoodsBanner.setImageLoader(new GoodsLoader());

        mBinding.viewGoodsBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    public void setGoods(List<String> goods) {
        mGoods.clear();
        if (goods != null) {
            mGoods.addAll(goods);
        }

        mGoodsShowAdapter.notifyDataSetChanged();
        List<List<String>> list = new ArrayList<>();
        list.clear();
        list.add(goods);
        mBinding.viewGoodsBanner.setImages(list);
        mBinding.viewGoodsBanner.start();
        mBinding.viewGoodsBanner.stopAutoPlay();
    }


    public class GoodsShowAdapter extends BaseGlideAdapter<String> {

        public GoodsShowAdapter(@Nullable List<String> data) {
            super(R.layout.item_goods_show, data);
        }

        @Override
        protected void convert(@NonNull GlideViewHolder helper, String item) {
            helper.loadRoundedCornersImage(R.id.iv_goods_icon, R.mipmap.banner_1, 4);
            helper.setText(R.id.price, helper.getLayoutPosition() + "");
        }
    }
}
