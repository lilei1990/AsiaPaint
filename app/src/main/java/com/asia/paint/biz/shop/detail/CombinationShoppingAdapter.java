package com.asia.paint.biz.shop.detail;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情组合购列表适配器
 *
 * @author tangkun
 */
public class CombinationShoppingAdapter extends BaseGlideAdapter<PromotionComposeRsp.ResultBean> {
	public CombinationShoppingAdapter() {
		super(R.layout.item_combination_shopping);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PromotionComposeRsp.ResultBean item) {
		if (item != null) {
			if (helper.getLayoutPosition() == (getItemCount() - 1)) {
				helper.setGone(R.id.divider, false);
			} else {
				helper.setGone(R.id.divider, true);
			}
			helper.addOnClickListener(R.id.tv_combination_shopping_buy);
			CombinationShoppingItemAdapter mCombinationShoppingItemAdapter = new CombinationShoppingItemAdapter(item.market_price, item.price);
			List<PromotionComposeRsp.ResultBean.GoodsBean> itemDataList = new ArrayList<>();
			if (item._goods != null) {
				itemDataList.addAll(item._goods);
			}
			mCombinationShoppingItemAdapter.updateData(itemDataList);
			RecyclerView recyclerView = helper.itemView.findViewById(R.id.rv_combination_shopping_item);
			recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
			recyclerView.setAdapter(mCombinationShoppingItemAdapter);
			//增加跳转商品详情功能
			mCombinationShoppingItemAdapter.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					GoodsDetailActivity.start(mContext, Integer.valueOf(item._goods.get(position).goods_id));
				}
			});
		}
	}
}
