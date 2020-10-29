package com.asia.paint.base.widgets;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.asia.paint.android.R;
import com.asia.paint.base.model.AddCartViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.biz.mine.service.CustomerServiceActivity;
import com.asia.paint.biz.mine.vip.TR_IMActivity;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.android.databinding.ViewBottomCartBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.smarttop.library.utils.LogUtil;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public class BottomCart extends LinearLayout implements OnChangeCallback<Integer> {

    private ViewBottomCartBinding mBinding;
    private ShopGoodsDetailRsp mShopGoodsDetailRsp;
    private Goods mGoods;
    private Specs mSpecs;
    private int mType;

    private int mCount;
    private AddCartViewModel mCartViewModel = new AddCartViewModel();
    private int mGoodsId;
    private int mGroupById;
    private int mSpikeId;

    public BottomCart(Context context) {
        this(context, null);
    }

    public BottomCart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomCart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_bottom_cart, this, true);
        init();
    }

    private void init() {
        AsiaPaintApplication.addCartCountCallback(this);
        AsiaPaintApplication.queryCartCount();
        mBinding.btnAddCart.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (hasSelectedSpecAndCount()) {
                    mCartViewModel.addCart(mSpecs.spec_id, mCount);
                } else {
                    mCartViewModel.showGoodsSpecDialog(getContext(), 0, 0, mSpecs, mCount, mGoods, GoodsSpecDialog.Type.CART);
                }
            }
        });
        mBinding.btnBuy.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                //直接购买
                int id = 0;
                if (mType == OrderService.BUY) {
                    id = 0;
                } else if (mType == OrderService.GROUP) {//团购
                    if (mShopGoodsDetailRsp != null && mShopGoodsDetailRsp._groupbuy != null)
                        id = mShopGoodsDetailRsp._groupbuy.groupbuy_id;
                } else if (mType == OrderService.SPIKE) {//秒杀
                    if (mShopGoodsDetailRsp != null && mShopGoodsDetailRsp._spike != null)
                        id = mShopGoodsDetailRsp._spike.spike_id;
                }
                if (hasSelectedSpecAndCount()) {
                    LogUtil.e("mType1", mType + "");
                    //直接购买
                    if (mType == OrderService.BUY) {
                        mCartViewModel.directBuy(mSpecs.spec_id, mCount).setCallback(new OnChangeCallback<Boolean>() {
                            @Override
                            public void onChange(Boolean result) {
                                if (result)
                                    OrderCheckoutActivity.start(getContext(), OrderService.BUY, mSpecs.spec_id, mCount);
                            }
                        });
                    } else if (mType == OrderService.GROUP) {//团购
                        if (mShopGoodsDetailRsp != null && mShopGoodsDetailRsp._groupbuy != null)
                            OrderCheckoutActivity.start(getContext(), OrderService.GROUP, mSpecs.spec_id, mCount, id);
                    } else if (mType == OrderService.SPIKE) {//秒杀
                        if (mShopGoodsDetailRsp != null && mShopGoodsDetailRsp._spike != null)
                            OrderCheckoutActivity.start(getContext(), OrderService.SPIKE, id, mCount);
                    }
                } else {
                    mCartViewModel.showGoodsSpecDialog(getContext(), mType, id, mSpecs, mCount, mGoods, GoodsSpecDialog.Type.BUY);
                }
            }
        });

        mBinding.tvGoShop.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MainActivity.start(getContext(), MainActivity.Tab.SHOP.getValue());
            }
        });
        mBinding.tvGoCart.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MainActivity.start(getContext(), MainActivity.Tab.CART.getValue());
            }
        });
        mBinding.tvToCustomerService.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
//                CustomerServiceActivity.start(getContext(), 0);
//                改为天润客服
                ActivityUtils.startActivity(TR_IMActivity.class);
            }
        });
    }

    private boolean hasSelectedSpecAndCount() {
        return mSpecs != null && mCount > 0;
    }


    /**
     * 是否隐藏加入购物车按钮
     */
    public void hideAddCar(boolean isHideAddCar) {
        if (isHideAddCar) {
            mBinding.btnAddCart.setVisibility(GONE);
        } else {
            mBinding.btnAddCart.setVisibility(VISIBLE);
        }
    }

    /**
     * 是否隐藏加入购物车按钮
     */
    public void setBtnBuyContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            mBinding.btnBuy.setText(content);
        } else {
            mBinding.btnBuy.setText("");
        }
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public ShopGoodsDetailRsp getShopGoodsDetailRsp() {
        return mShopGoodsDetailRsp;
    }

    public void setShopGoodsDetailRsp(ShopGoodsDetailRsp mShopGoodsDetailRsp) {
        this.mShopGoodsDetailRsp = mShopGoodsDetailRsp;
    }

    public Goods getGoods() {
        return mGoods;
    }

    public void setGoods(Goods goods) {
        mGoods = goods;
    }


    public void setSpecs(Specs specs) {
        mSpecs = specs;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public void setCartCount(int count) {
        mBinding.tvRedDot.setVisibility(count > 0 ? VISIBLE : GONE);
        mBinding.tvRedDot.setText(String.valueOf(count));
    }

    public void removeCartCountCallback() {
        AsiaPaintApplication.removeCartCountCallback(this);
    }

    @Override
    public void onChange(Integer result) {
        setCartCount(result);
    }
}
