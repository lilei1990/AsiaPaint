package com.asia.paint.biz.mine.vip.flash;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.biz.mine.vip.Dialog.VipGoodsSpecDialog;
import com.asia.paint.biz.mine.vip.VipGoodViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.PriceUtils;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-05.
 */
public class VipGoodsAdapter extends BaseGlideAdapter<Goods> {

    private OnClickChildListener mChildListener;
    private FragmentActivity mActivity;
    private VipGoodViewModel mCartViewModel;

    public VipGoodsAdapter(@Nullable List<Goods> data, FragmentActivity activity) {
        super(R.layout.item_goods, data);
        mActivity = activity;
        mCartViewModel =
                ViewModelProviders.of(mActivity).get(VipGoodViewModel.class);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Goods goods) {

        String iconUrl = "";
        if (goods.default_image != null && goods.default_image.size() > 0) {
            iconUrl = goods.default_image.get(0);
        }
        helper.loadImage(R.id.iv_goods_icon, iconUrl);
        helper.setText(R.id.tv_goods_name, goods.goods_name);
        String sellCount = String.format("已售卖 %s", goods.sales);
        helper.setText(R.id.tv_sell_count, sellCount);
        helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(goods.price));
        View ivCollect = helper.getView(R.id.iv_goods_collect);
        ivCollect.setSelected(goods.isCollect());
        ivCollect.setVisibility(View.GONE);
        //选择规格
        helper.setText(R.id.btn_add_cart, "选择规格");

        helper.setOnClickListener(R.id.btn_add_cart, new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String icon = "";
                if (goods.default_image != null && !goods.default_image.isEmpty()) {
                    icon = goods.default_image.get(0);
                }
                mCartViewModel.showVipGoodsSpecDialog(mActivity, 0, 0, null, 1, goods, VipGoodsSpecDialog.Type.CART);
//                VipGoodsSpecDialog dialog = new VipGoodsSpecDialog.Builder()
//                        .setType(VipGoodsSpecDialog.Type.SPEC)
//                        .setIconUrl(icon)
//                        .setSpec(null)
//                        .setCount(1)
//                        .setSpecList(goods._specs)
//                        .build();
//                showGoodsSpecDialog(dialog);
//            if (mChildListener != null) {
//                mChildListener.onAddCart(goods);
//            }
            }
        });
    }

    public void setOnChildLisenter(OnClickChildListener childLisenter) {
        mChildListener = childLisenter;
    }


    public interface OnClickChildListener {
        void onAddCart(Goods goods);

        void onCollect(Goods goods);
    }
}
