package com.asia.paint.base.image;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.asia.paint.R;
import com.asia.paint.banner.loader.ImageLoaderInterface;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.SmartRecyclerView;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public class GoodsLoader implements ImageLoaderInterface<View> {

    private GoodsShowAdapter mGoodsShowAdapter;
    private List<String> mGoods = new ArrayList<>();

    @Override
    public void displayImage(Context context, Object goods, View imageView) {
        mGoods.clear();
        if (goods instanceof List) {
            mGoods.addAll((Collection<? extends String>) goods);
        }
        Log.i("hongc", "displayImage: "+ goods.toString());
        mGoodsShowAdapter.notifyDataSetChanged();
    }

    @Override
    public View createImageView(Context context, Object url) {
        SmartRecyclerView recyclerView = new SmartRecyclerView(context);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = AppUtils.dp2px(19);
        recyclerView.setLayoutParams(params);
        mGoodsShowAdapter = new GoodsShowAdapter(mGoods);
        recyclerView.setAdapter(mGoodsShowAdapter);
        return recyclerView;
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
