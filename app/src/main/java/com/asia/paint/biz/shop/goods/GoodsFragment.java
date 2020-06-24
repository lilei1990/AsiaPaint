package com.asia.paint.biz.shop.goods;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.model.CollectViewModel;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.biz.main.MainViewModel;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.databinding.FragmentGoodsBinding;

import java.util.ArrayList;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class GoodsFragment extends BaseFragment<FragmentGoodsBinding> {

    private static final String KEY_CATEGORY = "key_category";
    private GoodsTagAdapter mTagAdapter;
    private GoodsAdapter mGoodsAdapter;
    private ShopBannerRsp.CategoryBean mCategory;
    private GoodsViewModel mViewModel;
    private GoodsViewModel.Sort mSort;

    public static GoodsFragment create(ShopBannerRsp.CategoryBean category) {
        GoodsFragment goodsFragment = new GoodsFragment();
        if (category != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_CATEGORY, category);
            goodsFragment.setArguments(bundle);
        }
        return goodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCategory = arguments.getParcelable(KEY_CATEGORY);
        }
        mViewModel = getViewModel(GoodsViewModel.class);
        mViewModel.setCategory(mCategory);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        reset();
        initTag();
        initGoods();
        loadGoods();
    }

    private void reset() {
        mViewModel.resetPage();
        mSort = GoodsViewModel.Sort.DESC;
    }

    private void initTag() {
        mBinding.rvTag.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mTagAdapter = new GoodsTagAdapter(mViewModel.getGoodsCategory());
        mBinding.rvTag.setAdapter(mTagAdapter);
        mTagAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (TextUtils.equals(mTagAdapter.getCurTag(), "价格") && mTagAdapter.isPriceTag(position)) {
                mViewModel.resetPage();
                mTagAdapter.switchTag();
                mSort = mTagAdapter.getSort();
            } else {
                reset();
            }
            mTagAdapter.setSelectedPosition(position);
            mTagAdapter.setSort(mTagAdapter.isPriceTag(position) ? mSort : null);
            loadGoods();
        });
    }

    private void initGoods() {
        mBinding.rvGoods.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        mBinding.rvGoods.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mGoodsAdapter = new GoodsAdapter(new ArrayList<>());
        mBinding.rvGoods.setAdapter(mGoodsAdapter);
        mGoodsAdapter.disableLoadMoreIfNotFullPage(mBinding.rvGoods);
        mGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            Goods goods = mGoodsAdapter.getData().get(position);
            GoodsDetailActivity.start(mContext, goods.goods_id);
        });
        mGoodsAdapter.setOnChildLisenter(new GoodsAdapter.OnClickChildListener() {
            @Override
            public void onAddCart(Goods goods) {
                getMainViewModel().addCart(goods.default_spec, 1);
            }

            @Override
            public void onCollect(Goods goods) {
                if (goods.isCollect()) {
                    getCollectViewModel().delCollect(goods.goods_id).setCallback(result -> {
                        goods.is_collect = 0;
                        mGoodsAdapter.notifyDataSetChanged();
                    });
                } else {
                    getCollectViewModel().addCollect(goods.goods_id).setCallback(result -> {
                        goods.is_collect = 1;
                        mGoodsAdapter.notifyDataSetChanged();
                    });
                }
            }
        });
        mGoodsAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadGoods();
        }, mBinding.rvGoods);

    }

    private void loadGoods() {
        mViewModel.loadGoodsByTag(mTagAdapter.getCurTag(), mSort).setCallback(result -> mViewModel.updateListData(mGoodsAdapter, result));
    }


    private MainViewModel getMainViewModel() {
        MainViewModel viewModel = null;
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            viewModel = ((MainActivity) activity).getMainViewModel();
        }
        if (viewModel == null) {
            viewModel = new MainViewModel();
        }
        return viewModel;
    }

    private CollectViewModel getCollectViewModel() {
        CollectViewModel viewModel = null;
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            viewModel = ((MainActivity) activity).getCollectViewModel();
        }
        if (viewModel == null) {
            viewModel = new CollectViewModel();
        }
        return viewModel;
    }
}
