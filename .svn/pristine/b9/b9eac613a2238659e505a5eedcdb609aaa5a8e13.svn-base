package com.asia.paint.biz.order.mine;

import android.content.Context;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.FoldPanel;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.PriceUtils;

import static com.asia.paint.biz.order.mine.OrderMineActivity.ORDER_TYPE;

/**
 * @author by chenhong14 on 2019-11-26.
 */
public class OrderMineAdapter extends BaseGlideAdapter<OrderDetail> {

	private OnChangeCallback<OrderDetail> mOnChangeCallback;

	public OrderMineAdapter(Context mContext) {
		super(mContext, R.layout.item_order_mine);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, OrderDetail item) {
		if (item != null) {
			helper.setText(R.id.tv_date, item.add_time);
			helper.setText(R.id.tv_description, getType(item.order_status));
			FoldPanel<OrderDetail.Goods> foldPanel = helper.getView(R.id.view_fold_panel);
			foldPanel.setDefaultShowCount(2);
			OrderMineGoodsAdapter mineGoodsAdapter = new OrderMineGoodsAdapter(false);
			mineGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
				if (mOnChangeCallback != null) {
					mOnChangeCallback.onChange(item);
				}
			});
			foldPanel.setAdapter(mineGoodsAdapter);
			foldPanel.setDatas(item._goods);
			helper.setText(R.id.tv_price, PriceUtils.getPrice(item.order_amount));
			helper.addOnClickListener(R.id.tv_cancel, R.id.tv_delete, R.id.tv_pay, R.id.tv_sure_receive, R.id.tv_comment);
			showCancel(helper, item);
			showPay(helper, item);
			showDelete(helper, item);
			showSureReceive(helper, item);
			showComment(helper, item);
			helper.itemView.setOnClickListener(v -> {
				if (mOnChangeCallback != null) {
					mOnChangeCallback.onChange(item);
				}
			});
		}
	}

	private String getType(int type) {
		if (type == OrderService.ORDER_ALL) {
			return "";
		}
		if (type == OrderService.ORDER_DONE) {
			return "已完成";
		}
		return ORDER_TYPE.get(type);
	}


	private void showCancel(GlideViewHolder helper, OrderDetail item) {
		boolean show = item.order_status == OrderService.ORDER_PAY || item.order_status == OrderService.ORDER_DELIVERY
				|| item.order_status == OrderService.ORDER_DELIVERY;
		helper.setGone(R.id.tv_cancel, show);
	}

	private void showPay(GlideViewHolder helper, OrderDetail item) {
		boolean show = item.order_status == OrderService.ORDER_PAY;
		helper.setGone(R.id.tv_pay, show);
	}

	private void showDelete(GlideViewHolder helper, OrderDetail item) {
     /*   boolean show = item.order_status == OrderService.ORDER_DONE
                || item.order_status == OrderService.ORDER_CANCEL;*/
		//目前取消才能删除
		boolean show = item.order_status == OrderService.ORDER_CANCEL;
		helper.setGone(R.id.tv_delete, show);
	}

	private void showSureReceive(GlideViewHolder helper, OrderDetail item) {
		boolean show = item.order_status == OrderService.ORDER_RECEIVE;
		helper.setGone(R.id.tv_sure_receive, show);
	}

	private void showComment(GlideViewHolder helper, OrderDetail item) {
		boolean show = item.order_status == OrderService.ORDER_COMMENT;
		helper.setGone(R.id.tv_comment, show);
	}

	public void setOnChangeCallback(OnChangeCallback<OrderDetail> onChangeCallback) {
		mOnChangeCallback = onChangeCallback;
	}
}
