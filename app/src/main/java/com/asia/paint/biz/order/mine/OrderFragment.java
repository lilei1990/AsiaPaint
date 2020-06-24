package com.asia.paint.biz.order.mine;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.network.bean.OrderMineRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.widgets.MessageDialog;
import com.asia.paint.biz.comment.add.AddCommentActivity;
import com.asia.paint.biz.order.OrderViewModel;
import com.asia.paint.biz.order.mine.detail.OrderDetailActivity;
import com.asia.paint.biz.pay.pay.PayTypeViewModel;
import com.asia.paint.databinding.FragmentOrderBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-26.
 */
public class OrderFragment extends BaseFragment<FragmentOrderBinding> {

    private static final String KEY_ORDER_TYPE_FRAGMENT = "KEY_ORDER_TYPE_FRAGMENT";
    private int mType;
    private OrderViewModel mOrderViewModel;
    private OrderMineAdapter mOrderMineAdapter;
    private PayTypeViewModel mPayTypeViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    public static OrderFragment createInstance(int type) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ORDER_TYPE_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_ORDER_TYPE_FRAGMENT);
    }

    @Override
    protected void initView() {
        mOrderViewModel = getViewModel(OrderViewModel.class);
        mBinding.rvOrderMine.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvOrderMine.addItemDecoration(new DefaultItemDecoration(14, 12, 14, 0));
        mBinding.rvOrderMine.setItemAnimator(null);
        mOrderMineAdapter = new OrderMineAdapter(getActivity());
        mOrderMineAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderDetail orderDetail = mOrderMineAdapter.getData().get(position);
            int id = view.getId();
            switch (id) {
                case R.id.tv_cancel:
                    new MessageDialog.Builder()
                            .title("确认取消该订单吗？")
                            .setCancelButton("返回", null)
                            .setSureButton("确认", new OnNoDoubleClickListener() {
                                @Override
                                public void onNoDoubleClick(View view) {
                                    mOrderViewModel.cancelOrder(orderDetail.order_id).setCallback(result -> {
                                        orderDetail.order_status = OrderService.ORDER_CANCEL;
                                        mOrderMineAdapter.notifyItemChanged(position);
                                    });
                                }
                            }).build()
                            .show(mContext);
                    break;
                case R.id.tv_delete:
                    new MessageDialog.Builder()
                            .title("确认删除该订单吗？")
                            .setCancelButton("取消", null)
                            .setSureButton("确认", new OnNoDoubleClickListener() {
                                @Override
                                public void onNoDoubleClick(View view) {
                                    mOrderViewModel.delOrder(orderDetail.order_id).setCallback(result -> {
                                        mOrderMineAdapter.remove(position);
                                        mOrderMineAdapter.notifyItemRemoved(position);
                                    });
                                }
                            }).build()
                            .show(mContext);
                    break;
                case R.id.tv_sure_receive:
                    new MessageDialog.Builder()
                            .title("确认收货吗？")
                            .setCancelButton("取消", null)
                            .setSureButton("确认", new OnNoDoubleClickListener() {
                                @Override
                                public void onNoDoubleClick(View view) {
                                    mOrderViewModel.orderIsReceive(orderDetail.order_id).setCallback(result -> {
                                        orderDetail.order_status = OrderService.ORDER_DONE;
                                        mOrderMineAdapter.notifyItemChanged(position);
                                    });
                                }
                            }).build()
                            .show(mContext);
                    break;
                case R.id.tv_pay:
                    mPayTypeViewModel = new PayTypeViewModel(mContext, orderDetail.order_id, orderDetail.order_amount);
                    mPayTypeViewModel.startPay().setCallback(result -> {
                        mPayTypeViewModel = null;
                        if (result) {
                            orderDetail.order_status = OrderService.ORDER_DELIVERY;
                            mOrderMineAdapter.notifyItemChanged(position);
                        }
                    });
                    break;
                case R.id.tv_comment:
                    List<OrderDetail.Goods> goods = orderDetail._goods;
                    if (goods != null && !goods.isEmpty()) {
                        AddCommentActivity.start(mContext, goods.get(0));
                    }
                    break;
            }
        });
        mOrderMineAdapter.setOnChangeCallback(result -> OrderDetailActivity.start(mContext, result.order_id));
        mBinding.rvOrderMine.setAdapter(mOrderMineAdapter);
        mOrderMineAdapter.setOnLoadMoreListener(() -> {
            mOrderViewModel.autoAddPage();
            loadOrder();
        }, mBinding.rvOrderMine);

    }

    @Override
    public void onResume() {
        super.onResume();
        mOrderViewModel.resetPage();
        loadOrder();
        if (mPayTypeViewModel != null) {
            mPayTypeViewModel.onResume();
        }
    }

    private void loadOrder() {
        if (mType == OrderService.ORDER_COMMENT) {
            mOrderViewModel.commentOrder().setCallback(result -> {
                OrderMineRsp mineRsp = null;
                if (result != null) {
                    mineRsp = new OrderMineRsp();
                    mineRsp.totalpage = result.totalpage;
                    List<OrderDetail.Goods> goodsList = result.data;
                    List<OrderDetail> orderDetails = new ArrayList<>();
                    if (goodsList != null) {
                        for (OrderDetail.Goods goods : goodsList) {
                            if (goods != null) {
                                OrderDetail detail = new OrderDetail();
                                detail.order_status = OrderService.ORDER_COMMENT;
                                detail.add_time = goods.add_time;
                                detail.order_id = goods.order_id;
                                List<OrderDetail.Goods> commentGoods = new ArrayList<>();
                                commentGoods.add(goods);
                                detail._goods = commentGoods;
                                detail.order_amount = goods.goods_price;
                                orderDetails.add(detail);
                            }
                        }
                    }
                    mineRsp.data = orderDetails;
                }
                mOrderViewModel.updateListData(mOrderMineAdapter, mineRsp);
            });
        } else {
            mOrderViewModel.loadMyOrder(mType, mOrderViewModel.getCurPage())
                    .setCallback(result ->
                            mOrderViewModel.updateListData(mOrderMineAdapter, result));
        }
    }

}
