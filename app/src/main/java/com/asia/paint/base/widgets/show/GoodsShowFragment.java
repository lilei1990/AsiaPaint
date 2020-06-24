package com.asia.paint.base.widgets.show;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.databinding.FragmentGoodsShowBinding;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * @author by chenhong14 on 2019-12-14.
 */
public class GoodsShowFragment extends BaseFragment<FragmentGoodsShowBinding> {

    private static final String KEY_SHOW_PAGE = "KEY_SHOW_PAGE";
    private static final String KEY_SHOW_CATE_ID = "KEY_SHOW_CATE_ID";
    private GoodsShowAdapter mGoodsShowAdapter;
    private int mPage;
    private int mCateId;

    public static GoodsShowFragment createInstance(int page, int cate_id) {
        GoodsShowFragment fragment = new GoodsShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_SHOW_PAGE, page);
        bundle.putInt(KEY_SHOW_CATE_ID, cate_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mPage = bundle.getInt(KEY_SHOW_PAGE);
        mCateId = bundle.getInt(KEY_SHOW_CATE_ID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_show;
    }

    @Override
    protected void initView() {
        GoodsShowViewModel goodsViewModel = getViewModel(GoodsShowViewModel.class);
        mBinding.rvGoodsShow.setLayoutManager(new GridLayoutManager(mContext, 3));
        mGoodsShowAdapter = new GoodsShowAdapter();
        mGoodsShowAdapter.setOnItemClickListener((adapter, view, position) -> {
            Goods goods = mGoodsShowAdapter.getData(position);
            if (goods != null) {
                GoodsDetailActivity.start(mContext, goods.goods_id);
            }

        });
        mBinding.rvGoodsShow.setAdapter(mGoodsShowAdapter);
        goodsViewModel.loadShowGoods(mPage, mCateId).setCallback(result -> mGoodsShowAdapter.replaceData(result.data));
    }
}
