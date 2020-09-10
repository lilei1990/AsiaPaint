package com.asia.paint.biz.order.mine.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityOrderDetailBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.OrderCheckout;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.network.bean.OrderReturnDetailRsp;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.comment.add.AddCommentActivity;
import com.asia.paint.biz.order.OrderViewModel;
import com.asia.paint.biz.order.checkout.OrderCheckoutAdapter;
import com.asia.paint.biz.order.mine.OrderMineGoodsAdapter;
import com.asia.paint.biz.order.mine.aftersale.apply.SelectAfterSaleTypeActivity;
import com.asia.paint.biz.pay.pay.PayTypeViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class OrderDetailActivity extends BaseTitleActivity<ActivityOrderDetailBinding> {

	public static SparseArray<String> mOrderStatusTips;

	static {
		mOrderStatusTips = new SparseArray<>();
		mOrderStatusTips.append(OrderService.ORDER_PAY, "等待买家付款\n下单后30分钟内未付款，订单自动取消");
		mOrderStatusTips.append(OrderService.ORDER_DELIVERY, "等待卖家发货\n");
		mOrderStatusTips.append(OrderService.ORDER_RECEIVE, "卖家已发货\n");
		mOrderStatusTips.append(OrderService.ORDER_COMMENT, "等待买家评价\n");
		mOrderStatusTips.append(OrderService.ORDER_CANCEL, "交易关闭\n");
		mOrderStatusTips.append(OrderService.ORDER_DONE, "交易成功\n");
	}

	private OrderViewModel mOrderViewModel;
	private OrderMineGoodsAdapter mGoodsAdapter;
	private int mOrderId;
	private OrderReturnDetailRsp.OrderReturnDetail mOrderDetailRsp;

	private static final String KEY_ORDER_ID = "KEY_ORDER_ID";
	private OrderCheckoutAdapter mCheckoutAdapter;
	private PayTypeViewModel mPayTypeViewModel;

	public static void start(Context context, int orderId) {
		Intent intent = new Intent(context, OrderDetailActivity.class);
		intent.putExtra(KEY_ORDER_ID, orderId);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mOrderId = intent.getIntExtra(KEY_ORDER_ID, mOrderId);
	}

	@Override
	protected String middleTitle() {
		return "订单详情";
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_order_detail;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOrderViewModel = getViewModel(OrderViewModel.class);
		mBinding.rvGoods.setLayoutManager(new LinearLayoutManager(this));
		mGoodsAdapter = new OrderMineGoodsAdapter(true);
		mGoodsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			OrderDetail.Goods goods = mGoodsAdapter.getData(position);
			int id = view.getId();
			if (id == R.id.tv_after_sale) {
				boolean overdue = mOrderDetailRsp != null && mOrderDetailRsp.return_status == 0;
				SelectAfterSaleTypeActivity.start(OrderDetailActivity.this, goods, overdue);
			}
		});
		mBinding.rvGoods.setAdapter(mGoodsAdapter);
		mBinding.rvCheckout.setLayoutManager(new LinearLayoutManager(this));
		mCheckoutAdapter = new OrderCheckoutAdapter();
		mBinding.rvCheckout.setAdapter(mCheckoutAdapter);
		queryOrderDetail();
	}

	private void queryOrderDetail() {
		mOrderViewModel.queryOrderDetail(mOrderId).setCallback(this::updateOrderDetail);
	}

	private void updateOrderDetail(OrderReturnDetailRsp orderReturnDetailRsp) {
		if (orderReturnDetailRsp == null || orderReturnDetailRsp.orderReturnDetail == null) {
			return;
		}
		mOrderDetailRsp = orderReturnDetailRsp.orderReturnDetail;
		OrderReturnDetailRsp.OrderReturnDetail orderDetail = orderReturnDetailRsp.orderReturnDetail;
		setOrderStatus(mBinding.tvOrderStatus, getOrderStatus(orderReturnDetailRsp.orderReturnDetail));
		setUserInfo(orderDetail);
		mGoodsAdapter.setOrderStatus(orderDetail.order_status);
		mGoodsAdapter.updateData(orderDetail._goods);
		setCheckout(orderDetail._list);
		setOrderInfo(orderDetail);
		setBottomPanel(orderDetail);
	}

	private void setCheckout(List<OrderReturnDetailRsp.OrderReturnDetail.Checkout> checkoutList) {
		if (checkoutList == null) {
			return;
		}
		List<OrderCheckout> checkouts = new ArrayList<>();
		for (OrderReturnDetailRsp.OrderReturnDetail.Checkout checkout : checkoutList) {
			if (checkout != null) {
				checkouts.add(new OrderCheckout(checkout.name, checkout.value, checkout.color, 0));
			}
		}
		mCheckoutAdapter.updateData(checkouts, true);
	}


	public static void setOrderStatus(TextView textView, int status) {

		String statusTip = mOrderStatusTips.get(status);
		if (TextUtils.isEmpty(statusTip)) {
			statusTip = " \n ";
		}
		textView.setText(statusTip);
	}

	private int getOrderStatus(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		int status = orderDetail.order_status;
		if (status == OrderService.ORDER_DONE) {
			List<OrderDetail.Goods> goodsList = orderDetail._goods;
			if (goodsList != null && !goodsList.isEmpty()) {
				OrderDetail.Goods goods = goodsList.get(0);
				if (goods != null && goods.comment_status == 0) {
					status = OrderService.ORDER_COMMENT;
				}
			}
		}
		return status;
	}

	private void setUserInfo(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		mBinding.tvUserName.setText(String.format("姓名：%s", orderDetail.consignee));
		mBinding.tvUserPhone.setText(orderDetail.tel);
		mBinding.tvUserAddress.setText(String.format("%s%s", orderDetail.address, orderDetail.address_name));
	}

	private void setOrderInfo(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {

		StringBuilder builder = new StringBuilder();
		builder.append("订单编号：").append(orderDetail.order_sn).append("\n");
		builder.append("创建时间：").append(orderDetail.add_time).append("\n");
		if (!TextUtils.isEmpty(orderDetail.pay_time)) {
			builder.append("付款时间：").append(orderDetail.pay_time).append("\n");
		}

		if (!TextUtils.isEmpty(orderDetail.send_time) && orderDetail.send_time.length() > 1) {
			builder.append("发货时间：").append(orderDetail.send_time).append("\n");
		}
		if (!TextUtils.isEmpty(orderDetail.express_sn)) {
			if (!TextUtils.isEmpty(orderDetail.receive_time)) {
				builder.append("成交时间：").append(orderDetail.receive_time).append("\n");
			}
		} else {
			if (!TextUtils.isEmpty(orderDetail.receive_time)) {
				builder.append("成交时间：").append(orderDetail.receive_time);
			}
		}
		if (!TextUtils.isEmpty(orderDetail.express_company)) {
			builder.append("物流公司：").append(orderDetail.express_company).append("\n");
		}
		if (!TextUtils.isEmpty(orderDetail.express_sn)) {
			builder.append("物流单号：").append(orderDetail.express_sn);
		}
		mBinding.tvOrderInfo.setText(builder.toString());
	}

	private void setBottomPanel(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		showCancel(orderDetail);
		showPay(orderDetail);
		showDelete(orderDetail);
		showSureReceive(orderDetail);
		showComment(orderDetail);
	}

	private void showCancel(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		int status = getOrderStatus(orderDetail);
		boolean show = status == OrderService.ORDER_PAY || status == OrderService.ORDER_DELIVERY
				|| status == OrderService.ORDER_DELIVERY;
		mBinding.tvCancel.setVisibility(show ? View.VISIBLE : View.GONE);
		mBinding.tvCancel.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				new MessageDialog.Builder()
						.title("确认取消该订单吗？")
						.setCancelButton("返回", null)
						.setSureButton("确认", new OnNoDoubleClickListener() {
							@Override
							public void onNoDoubleClick(View view) {
								mOrderViewModel.cancelOrder(orderDetail.order_id).
										setCallback(result -> queryOrderDetail());
							}
						}).build()
						.show(OrderDetailActivity.this);
			}
		});
	}

	private void showPay(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		int status = getOrderStatus(orderDetail);
		boolean show = status == OrderService.ORDER_PAY;
		mBinding.tvPay.setVisibility(show ? View.VISIBLE : View.GONE);
		mBinding.tvPay.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mPayTypeViewModel = new PayTypeViewModel(OrderDetailActivity.this, orderDetail.order_id, orderDetail.order_amount);
				mPayTypeViewModel.startPay().setCallback(result -> {
					mPayTypeViewModel = null;
					if (result) {
						queryOrderDetail();
					}
				});
			}
		});
	}

	private void showDelete(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
     /*   boolean show = item.order_status == OrderService.ORDER_DONE
                || item.order_status == OrderService.ORDER_CANCEL;*/
		//目前取消才能删除
		int status = getOrderStatus(orderDetail);
		boolean show = status == OrderService.ORDER_CANCEL;
		mBinding.tvDelete.setVisibility(show ? View.VISIBLE : View.GONE);
		mBinding.tvDelete.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {

				new MessageDialog.Builder()
						.title("确认删除该订单吗？")
						.setCancelButton("取消", null)
						.setSureButton("确认", new OnNoDoubleClickListener() {
							@Override
							public void onNoDoubleClick(View view) {
								mOrderViewModel.delOrder(orderDetail.order_id).setCallback(result -> finish());
							}
						}).build()
						.show(OrderDetailActivity.this);
			}
		});
	}

	private void showSureReceive(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		int status = getOrderStatus(orderDetail);
		boolean show = status == OrderService.ORDER_RECEIVE;
		mBinding.tvSureReceive.setVisibility(show ? View.VISIBLE : View.GONE);
		mBinding.tvSureReceive.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mOrderViewModel.orderIsReceive(orderDetail.order_id).setCallback(result -> queryOrderDetail());
			}
		});
	}

	private void showComment(OrderReturnDetailRsp.OrderReturnDetail orderDetail) {
		int status = getOrderStatus(orderDetail);
		boolean show = status == OrderService.ORDER_COMMENT;
		mBinding.tvComment.setVisibility(show ? View.VISIBLE : View.GONE);
		mBinding.tvComment.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (orderDetail._goods != null && !orderDetail._goods.isEmpty()) {
					OrderDetail.Goods goods = orderDetail._goods.get(0);
					AddCommentActivity.start(OrderDetailActivity.this, goods);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mPayTypeViewModel != null) {
			mPayTypeViewModel.onResume();
		}
	}
}
