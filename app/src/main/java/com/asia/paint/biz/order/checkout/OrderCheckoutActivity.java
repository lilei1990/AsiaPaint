package com.asia.paint.biz.order.checkout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityOrderCheckoutBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.network.bean.Address;
import com.asia.paint.base.network.bean.Coupon;
import com.asia.paint.base.network.bean.CreateOrderRsp;
import com.asia.paint.base.network.bean.OrderCheckout;
import com.asia.paint.base.network.bean.OrderInfoRsp;
import com.asia.paint.base.network.bean.OrderScore;
import com.asia.paint.base.network.bean.Receipt;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.biz.mine.receipt.ReceiptDialog;
import com.asia.paint.biz.mine.receipt.ReceiptViewModel;
import com.asia.paint.biz.mine.settings.address.AddressActivity;
import com.asia.paint.biz.order.OrderViewModel;
import com.asia.paint.biz.order.group.GroupMemberAdapter;
import com.asia.paint.biz.pay.pay.PayTypeViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CommonUtil;
import com.asia.paint.utils.utils.DigitUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.asia.paint.utils.utils.SpanText;
import com.smarttop.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交订单
 *
 * @author by chenhong14 on 2019-11-10.
 */
public class OrderCheckoutActivity extends BaseActivity<ActivityOrderCheckoutBinding> {

    private static final String KEY_ORDER_TYPE = "KEY_ORDER_TYPE";
    private static final String KEY_ORDER_SPEC = "KEY_ORDER_SPEC";
    private static final String KEY_ORDER_COUNT = "KEY_ORDER_COUNT";
    private static final String KEY_ID = "KEY_ID";

    private OrderViewModel mOrderViewModel;

    private PayTypeViewModel mPayTypeViewModel;
    private ReceiptViewModel mReceiptViewModel;
    private int mType;
    private int mSpec;
    private int mBuyCount;
    /**
     * 团购ID和秒杀ID
     */
    private int mId;
    private OrderInfoRsp mOrderInfoRsp;
    private CreateOrderRsp mCreateOrderRsp;
    private OrderCheckout mCouponCheckout;
    private OrderCheckout mScoreCheckout;
    private OrderCheckout mFreightCheckout;

    private Address mAddress;
    private Coupon mCoupon;
    private List<OrderCheckout> mCheckouts = new ArrayList<>();
    private OrderCartAdapter mOrderCartAdapter;
    private OrderCheckoutAdapter mCheckoutAdapter;
    private GroupMemberAdapter mGroupMemberAdapter;
    private int mReceiptId;
    private List<Receipt> mReceipts = new ArrayList<>();


    public static void start(Context context, int type) {
        start(context, type, null, null);
    }

    public static void start(Context context, int type, Integer spec, Integer count) {
        Intent intent = new Intent(context, OrderCheckoutActivity.class);
        intent.putExtra(KEY_ORDER_TYPE, type);
        if (spec != null) {
            intent.putExtra(KEY_ORDER_SPEC, spec);
        }
        if (count != null) {
            intent.putExtra(KEY_ORDER_COUNT, count);
        }
        context.startActivity(intent);
    }

    public static void start(Context context, int type, Integer spec, Integer count, Integer mGroupBuyId) {
        Intent intent = new Intent(context, OrderCheckoutActivity.class);
        intent.putExtra(KEY_ORDER_TYPE, type);
        if (spec != null) {
            intent.putExtra(KEY_ORDER_SPEC, spec);
        }
        if (count != null) {
            intent.putExtra(KEY_ORDER_COUNT, count);
        }
        if (mGroupBuyId != null) {
            intent.putExtra(KEY_ID, mGroupBuyId);
        }
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mType = intent.getIntExtra(KEY_ORDER_TYPE, OrderService.CART);
        mSpec = intent.getIntExtra(KEY_ORDER_SPEC, 0);
        mBuyCount = intent.getIntExtra(KEY_ORDER_COUNT, 0);
        mId = intent.getIntExtra(KEY_ID, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_checkout;
    }

    @Override
    protected String getMiddleTitle() {
        return "提交订单";
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiptViewModel = getViewModel(ReceiptViewModel.class);
        mOrderViewModel = getViewModel(OrderViewModel.class);
        mBinding.layoutAddress.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                AddressActivity.start(OrderCheckoutActivity.this, AddressActivity.REQUEST_CODE_ORDER);
            }
        });
        //发票
        setReceipt();

        mBinding.rvGroupMember.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mGroupMemberAdapter = new GroupMemberAdapter();
        mBinding.rvGroupMember.setAdapter(mGroupMemberAdapter);

        mBinding.rvOrderGoods.setLayoutManager(new LinearLayoutManager(this));
        mOrderCartAdapter = new OrderCartAdapter();
        mBinding.rvOrderGoods.setAdapter(mOrderCartAdapter);

        mBinding.rvCheckout.setLayoutManager(new LinearLayoutManager(this));
        mCheckoutAdapter = new OrderCheckoutAdapter();
        mCheckoutAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                setFinalPayMoney();
            }
        });
        mBinding.rvCheckout.setAdapter(mCheckoutAdapter);

        mBinding.tvCheckout.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mAddress == null) {
                    AppUtils.showMessage("请选择地址");
                    return;
                }
                int couponId = mCoupon != null ? mCoupon.bonus_id : 0;
                //不开发票
                if (mReceiptId == 0) {
                    mOrderViewModel.createOrder(mType, mAddress.address_id, couponId,
                            mBinding.etCustomerMsg.getText().toString(), usedScore())
                            .setCallback(result -> {
                                mCreateOrderRsp = result;
                                startPay();
                            });
                } else {//开发票
                    mOrderViewModel.createOrder(mType, mAddress.address_id, couponId,
                            mBinding.etCustomerMsg.getText().toString(), usedScore(), usedReceipt())
                            .setCallback(result -> {
                                mCreateOrderRsp = result;
                                startPay();
                            });
                }

            }
        });
        LogUtil.e("mType2", mType + "");
        if (mType == OrderService.BUY) {
            //直接购买
            mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo);
//			mOrderViewModel.directBuy(mSpec, mBuyCount).setCallback(result -> mOrderViewModel.queryOrderInfo(mType, null)
//					.setCallback(this::updateOrderInfo));
        } else if (mType == OrderService.PROMOTION) {
            //组合购
            mOrderViewModel.promotionBuy(mType, mSpec, mBuyCount).setCallback(result ->
                    mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo));
        } else if (mType == OrderService.GROUP) {
            //团购
            mOrderViewModel.promotionBuy(mType, mId, mBuyCount, mSpec).setCallback(result ->
                    mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo));
        } else if (mType == OrderService.SPIKE) {
            //秒杀
            mOrderViewModel.promotionBuy(mType, mId, mBuyCount, mSpec).setCallback(result ->
                    mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo));
        } else if (mType == OrderService.APPLY_TASK) {
            //分销任务
            mOrderViewModel.promotionBuy(mType, mSpec).setCallback(result ->
                    mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo));
        } else if (mType == OrderService.APPLY_VIP_TASK) {
            //Vip任务
            mOrderViewModel.promotionBuy(mType, mSpec).setCallback(result ->
                    mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo));
        } else {
            mOrderViewModel.queryOrderInfo(mType, null).setCallback(this::updateOrderInfo);
        }
        loadReceipt(1);
    }

    private void loadReceipt(int page) {
        mReceiptViewModel.loadReceipt(page).setCallback(result -> {
            if (result != null && result.data != null) {
                mReceipts.addAll(result.data);
                if (page < result.totalpage) {
                    loadReceipt(page + 1);
                } else {
                    updateReceipt();
                }

            } else {
                updateReceipt();
            }
        });
    }

    private void updateReceipt() {
        Receipt showReceipt = null;
        for (Receipt receipt : mReceipts) {
            if (receipt != null && receipt.isDefault()) {
                showReceipt = receipt;
                break;
            }
        }
        setReceiptData(showReceipt);
    }

    private void setReceiptData(Receipt showReceipt) {
        if (showReceipt == null) {
            mReceiptId = 0;
            mBinding.tvReceipt.setTextColor(getResources().getColor(R.color.color_F41021));
            mBinding.tvReceipt.setText("不开发票");
            return;
        }
        mBinding.tvReceipt.setTextColor(getResources().getColor(R.color.color_333333));
        mReceiptId = showReceipt.id;
        String type = showReceipt.receipt == ReceiptService.TYPE_ELECTRONIC ? "电子" : "增值税专用发票";
        String name = showReceipt.title;
        SpanText spanText = new SpanText.Builder()
                .target(type)
                .origin(String.format("%s(%s)", type, name))
                .setSpan(new ForegroundColorSpan(AppUtils.getColor(R.color.color_F41021)))
                .build();
        mBinding.tvReceipt.setText(spanText.toSpan());
    }

    /**
     * 发票
     */
    private void setReceipt() {
        mBinding.layoutReceipt.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                int page = 1;
                mReceipts.clear();
                mReceiptViewModel.loadReceipt(page).setCallback(result -> {
                    if (result != null && result.data != null) {
                        mReceipts.addAll(result.data);
                        if (page < result.totalpage) {
                            loadReceipt(page + 1);
                        } else {
                            updateDialog();
                        }
                    } else {
                        updateDialog();
                    }
                });
            }
        });
    }

    private void updateDialog() {
        ReceiptDialog receiptDialog = new ReceiptDialog();
        receiptDialog.setReceipts(mReceipts);
        receiptDialog.setCallback((receipts, receipt) -> {
            mReceipts.clear();
            mReceipts.addAll(receipts);
            if (receipt != null)
                setReceiptData(receipt);
            else
                updateReceipt();
        });
        receiptDialog.show(OrderCheckoutActivity.this);
    }

    /**
     * 弹出支付窗口
     */
    private void startPay() {
        if (mCreateOrderRsp != null) {
            String money = mBinding.tvCheckoutPrice.getText().toString();
            mPayTypeViewModel = new PayTypeViewModel(OrderCheckoutActivity.this, mCreateOrderRsp.order_id,
                    money,mCreateOrderRsp.show_ye);
            mPayTypeViewModel.startPay().setCallback(result -> {
                mPayTypeViewModel = null;
                finish();
            });
        }
    }

    public void updateOrderInfo(OrderInfoRsp orderInfoRsp) {
        LogUtil.e("updateOrderInfo：", orderInfoRsp.toString());
        mOrderInfoRsp = orderInfoRsp;
        if (orderInfoRsp.myaddress != null && !orderInfoRsp.myaddress.isEmpty()) {
            setAddress(orderInfoRsp.myaddress.get(0));
        }
        mOrderCartAdapter.replaceData(orderInfoRsp.cart);
        setCoupon(orderInfoRsp.coupon);
        setScore(orderInfoRsp);
        setCheckoutPanel(orderInfoRsp);
        //拼团列表
        if (orderInfoRsp._pintuan == null || orderInfoRsp._pintuan.size() <= 0) {
            mBinding.llGroup.setVisibility(View.GONE);
        } else {
            mBinding.llGroup.setVisibility(View.VISIBLE);
            mGroupMemberAdapter.updateData(orderInfoRsp._pintuan);
        }
    }

    private void setScore(OrderInfoRsp orderInfoRsp) {
        if (orderInfoRsp.score_info == null) {
            return;
        }
        mBinding.layoutUsedScore.setVisibility(orderInfoRsp.isSupportUsedScore() ? View.VISIBLE : View.GONE);
        OrderScore scoreInfo = orderInfoRsp.score_info;
        mBinding.cbUsedScore.setListener(new CheckBox.OnDefaultCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                if (isChecked && scoreInfo != null) {
                    mScoreCheckout = new OrderCheckout("积分抵扣", "-" + PriceUtils.getPrice(String.valueOf(scoreInfo.discount_money)),
                            OrderCheckout.Color.RED, -scoreInfo.discount_money);
                } else {
                    mScoreCheckout = null;
                }
                List<OrderCheckout> newCheckout = new ArrayList<>(mCheckouts);
                if (mCouponCheckout != null) {
                    newCheckout.add(mCouponCheckout);
                }
                if (mScoreCheckout != null) {
                    newCheckout.add(mScoreCheckout);
                }
                mCheckoutAdapter.replaceData(newCheckout);
            }
        });

        if (scoreInfo != null) {
            mBinding.tvScoreTips.setText(scoreInfo.tip);
        }
    }

    private int usedScore() {
        if (mOrderInfoRsp != null && mOrderInfoRsp.score_info != null && mBinding.cbUsedScore.isChecked()) {
            return mOrderInfoRsp.score_info.discount_score;
        }
        return 0;
    }

    private int usedReceipt() {
        return mReceiptId;
    }

    public void setAddress(Address address) {
        mAddress = address;
        mBinding.layoutAddressDetail.setVisibility(address == null ? View.GONE : View.VISIBLE);
        mBinding.tvAddressTips.setVisibility(address == null ? View.VISIBLE : View.GONE);
        if (address != null) {
            mBinding.tvTagDefaultAddress.setVisibility(address.isDefault() ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(address.address)) {
                mBinding.tvArea.setText(address.address.replace(" ", ""));
            }
            mBinding.tvAddress.setText(address.address_name);
            mBinding.tvNameAndTel.setText(String.format("%s %s", address.consignee, CommonUtil.hidePhone(address.tel)));
        }
    }

    private void setCoupon(List<Coupon> coupons) {
        boolean isEmpty = coupons == null || coupons.isEmpty();
        mBinding.tvCoupon.setText(isEmpty ? "0张可用" : String.format("%s张可用", coupons.size()));
        if (isEmpty) {
            return;
        }
        mBinding.layoutCoupon.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderCouponDialog dialog = new OrderCouponDialog();
                dialog.setCoupons(coupons);
                dialog.setCallback(coupon -> {
                    dialog.dismiss();
                    mCoupon = coupon;
                    if (mCoupon == null) {
                        return;
                    }
                    mBinding.tvCoupon.setText(coupon.name);
                    List<OrderCheckout> newCheckout = new ArrayList<>(mCheckouts);
                    if (mScoreCheckout != null) {
                        newCheckout.add(mScoreCheckout);
                    }
                    mCouponCheckout = new OrderCheckout("优惠减免", "-" + PriceUtils.getPrice(coupon.money),
                            OrderCheckout.Color.RED, -DigitUtils.parseFloat(coupon.money));
                    newCheckout.add(mCouponCheckout);
                    mCheckoutAdapter.replaceData(newCheckout);
                });
                dialog.show(OrderCheckoutActivity.this);
            }
        });
    }

    public void setCheckoutPanel(OrderInfoRsp orderInfoRsp) {
        mCheckouts.clear();
        OrderCheckout amount = new OrderCheckout("商品金额", PriceUtils.getPrice(orderInfoRsp.amount), OrderCheckout.Color.BLACK,
                DigitUtils.parseFloat(orderInfoRsp.amount));
        mCheckouts.add(amount);
        mFreightCheckout = new OrderCheckout("运费", "+" + PriceUtils.getPrice(String.valueOf(orderInfoRsp.freight)), OrderCheckout.Color.RED,
                orderInfoRsp.freight);
        mCheckouts.add(mFreightCheckout);
        mCheckoutAdapter.replaceData(mCheckouts);
    }

    public void setFinalPayMoney() {
        mBinding.tvCheckoutPrice.setText(PriceUtils.getPrice(String.valueOf(mCheckoutAdapter.getFinalPrice())));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddressActivity.REQUEST_CODE_ORDER && data != null) {
            Address address = data.getParcelableExtra(AddressActivity.KEY_ADDRESS);
            if (address != null) {
                setAddress(address);
                queryFreight(address);
            }
        }
    }

    private void queryFreight(Address address) {
        mOrderViewModel.queryOrderInfo(mType, address.address_id).setCallback(result -> {
            if (mFreightCheckout != null && result != null) {
                mFreightCheckout.value = "+" + PriceUtils.getPrice(String.valueOf(result.freight));
                mFreightCheckout.price = result.freight;
                mCheckoutAdapter.notifyDataSetChanged();
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
