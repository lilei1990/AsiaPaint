package com.asia.paint.biz.order.mine.aftersale.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.base.widgets.CountView;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.biz.find.post.publish.PublishPostAdapter;
import com.asia.paint.biz.order.mine.aftersale.AfterSaleViewModel;
import com.asia.paint.databinding.ActivityApplyAfterSaleBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DigitUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.asia.paint.utils.utils.SpanText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-12-26.
 */
public class ApplyAfterSaleActivity extends BaseTitleActivity<ActivityApplyAfterSaleBinding> {

    private static final String KEY_RETURN_TYPE = "KEY_RETURN_TYPE";
    private static final String KEY_RETURN_TYPE_GOODS = "KEY_RETURN_TYPE_GOODS";
    private SparseArray<String> mTypeTitle;
    private SparseArray<String> mReturnReason;
    private PublishPostAdapter mPublishPostAdapter;

    {
        mTypeTitle = new SparseArray<>();
        mTypeTitle.put(SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY, "申请退款");
        mTypeTitle.put(SelectAfterSaleTypeActivity.TYPE_RETURN_GOODS, "申请退货退款");
        mTypeTitle.put(SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE, "申请换货");
        mReturnReason = new SparseArray<>();
        mReturnReason.put(SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY, "退款原因");
        mReturnReason.put(SelectAfterSaleTypeActivity.TYPE_RETURN_GOODS, "退货原因");
        mReturnReason.put(SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE, "换货原因");
    }

    private int mType;
    private OrderDetail.Goods mGoods;
    private AfterSaleViewModel mSaleViewModel;
    private float mReturnMoney;

    public static void start(Context context, int type, OrderDetail.Goods goods) {
        Intent intent = new Intent(context, ApplyAfterSaleActivity.class);
        intent.putExtra(KEY_RETURN_TYPE, type);
        if (goods != null) {
            intent.putExtra(KEY_RETURN_TYPE_GOODS, goods);
        }
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mType = intent.getIntExtra(KEY_RETURN_TYPE, SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY);
        mGoods = intent.getParcelableExtra(KEY_RETURN_TYPE_GOODS);
    }

    @Override
    protected String middleTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_after_sale;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaleViewModel = getViewModel(AfterSaleViewModel.class);
        setAfterSaleTitle();
        if (mGoods != null) {
            ImageUtils.load(mBinding.ivGoodsImg, mGoods.goods_image);
            mBinding.tvGoodsName.setText(mGoods.goods_name);
            mBinding.tvGoodsSpec.setText(String.format("规格：%s", mGoods.specification));
            setReturnMoney(mGoods.goods_numbers);
            mBinding.viewCount.setMaxCount(mGoods.goods_numbers);
            mBinding.viewCount.setCount(mGoods.goods_numbers);
        }
        mBinding.viewCount.setCallback(new CountView.CountViewCallback() {
            @Override
            public void onUpdate() {

            }

            @Override
            public void onChange(int count) {
                setReturnMoney(count);
            }
        });
        mBinding.etRemake.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        mBinding.etRemake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = 200 - s.length();
                if (length < 0) {
                    length = 0;
                }
                mBinding.tvRemakeCount.setText(String.valueOf(length));
            }
        });
        mBinding.layoutReason.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                new AfterSaleReasonDialog.Builder()
                        .setTitle(mTypeTitle.get(mType))
                        .setReason(getReturnReason())
                        .setChangeCallback(result -> mBinding.tvReturnReason.setText(result))
                        .build()
                        .show(ApplyAfterSaleActivity.this);
            }
        });
        mBinding.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mBinding.rvImage.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mBinding.rvImage.setItemAnimator(null);
        mPublishPostAdapter = new PublishPostAdapter();
        mBinding.rvImage.setAdapter(mPublishPostAdapter);
        mPublishPostAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.iv_delete) {
                mPublishPostAdapter.getData().remove(mPublishPostAdapter.getData(position));
                addImageUrls(new ArrayList<>());
                mPublishPostAdapter.notifyDataSetChanged();
            } else if (id == R.id.iv_img && mPublishPostAdapter.isAddImg(view.getTag())) {
                MatisseActivity.start(ApplyAfterSaleActivity.this, this::addImageUrls);
            }
        });
        addImageUrls(new ArrayList<>());
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                applyAfterSale();
            }
        });
        setAfterSaleType(mType);
    }

    private void setReturnMoney(int count) {
        float price = DigitUtils.parseFloat(mGoods.back_price, 0);
        mReturnMoney = price * count;
        String priceStr = PriceUtils.getPrice(String.valueOf(mReturnMoney));
        SpanText spanText = new SpanText.Builder()
                .origin(String.format("退款金额：%s", priceStr))
                .target(priceStr)
                .setSpan(new ForegroundColorSpan(AppUtils.getColor(R.color.color_F41021)) {
                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setTextSize(AppUtils.sp2px(14));
                    }
                })
                .build();
        mBinding.tvReturnMoney.setText(spanText.toSpan());
        mBinding.tvReturnMoneyTips.setText(String.format("最多可退%s元", priceStr));
    }

    private void setAfterSaleType(int type) {
        mBinding.layoutReturnGoods.setVisibility(type == SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY ? View.GONE : View.VISIBLE);
        mBinding.layoutReturnMoney.setVisibility(type == SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE ? View.GONE : View.VISIBLE);
        mBinding.tvReturnReasonTips.setText(mReturnReason.get(type));
    }

    private void setAfterSaleTitle() {
        String title = mTypeTitle.get(mType);
        mBaseBinding.tvTitle.setText(title);
    }

    private void addImageUrls(List<String> result) {
        mPublishPostAdapter.addImg(result, R.mipmap.ic_add_apply_after_sale);
    }

    private void applyAfterSale() {
        String reason = mBinding.tvReturnReason.getText().toString().trim();
        if (TextUtils.equals("请选择", reason)) {
            AppUtils.showMessage(String.format("请选择%s", mReturnReason.get(mType)));
            return;
        }
        String remake = mBinding.etRemake.getText().toString().trim();
        List<String> img = mPublishPostAdapter.getImg();
        if (img != null && !img.isEmpty()) {
            FileUtils.uploadMultiFile(FileService.SELLER, img).setCallback(result ->
                    requestAfterSale(reason, remake, result));
        } else {
            requestAfterSale(reason, remake, img);
        }
    }

    private void requestAfterSale(String reason, String desc, List<String> imageUrl) {
        int count = mBinding.viewCount.getCount();
        mSaleViewModel.applyAfterSale(mGoods.rec_id, mType, reason, count, String.valueOf(mReturnMoney), desc, imageUrl)
                .setCallback(result -> finish());
    }

    private List<String> getReturnReason() {
        List<String> reason = new ArrayList<>();
        switch (mType) {
            case SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY:
                break;
            case SelectAfterSaleTypeActivity.TYPE_RETURN_GOODS:
                reason.add("七天无理由退货");
                reason.add("未按约定时间送货");
                break;
            case SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE:
                reason.add("七天无理由退货");
                break;
        }
        reason.add("错发漏发");
        reason.add("商品破损");
        reason.add("质量问题");
        reason.add("商品信息与描述不符");
        return reason;
    }
}
