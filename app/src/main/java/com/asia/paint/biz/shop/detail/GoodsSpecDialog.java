package com.asia.paint.biz.shop.detail;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asia.paint.android.R;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.widgets.flowlayout.FlowLayout;
import com.asia.paint.base.widgets.flowlayout.TagAdapter;
import com.asia.paint.android.databinding.DialogGoodsSpecBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public class GoodsSpecDialog extends BaseBottomDialogFragment<DialogGoodsSpecBinding> {

    private static final String DEFAULT_SPEC_TIPS = "请选择规格属性";
    private Builder mBuilder;
    private OnClickGoodsSpecListener mListener;

    private GoodsSpecDialog(Builder builder) {
        mBuilder = builder;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_goods_spec;
    }

    @Override
    protected void initView() {
        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        Glide.with(mContext).load(mBuilder.iconUrl).placeholder(R.mipmap.ic_default).into(mBinding.ivGoodsIcon);
        mBinding.viewSpecTag.setMaxSelectCount(1);
        TagAdapter<Specs> tagAdapter = new TagAdapter<Specs>(mBuilder.mSpecsList) {

            @Override
            public View getView(FlowLayout parent, int position, Specs specs) {
                TextView textView = new TextView(parent.getContext());
                textView.setTextSize(12);
                int margin = AppUtils.dp2px(7);

                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, AppUtils.dp2px(26));
                params.setMargins(margin, margin, margin, margin);
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                int horizontal = AppUtils.dp2px(20);
                textView.setPadding(horizontal, 0, horizontal, 0);
                textView.setTextColor(AppUtils.getColor(R.color.color_goods_spec_text_selector));
                textView.setBackgroundResource(R.drawable.bg_goods_spec_selector);
                textView.setText(specs.spec_1);

                return textView;
            }
        };
        if (mBuilder.mSpec == null && mBuilder.mSpecsList != null && !mBuilder.mSpecsList.isEmpty()) {
            mBuilder.mSpec = mBuilder.mSpecsList.get(0);
        }
        tagAdapter.setSelectedData(mBuilder.mSpec);
        mBinding.viewSpecTag.setAdapter(tagAdapter);
        mBinding.viewSpecTag.setOnSelectListener(positions -> {
            Iterator<Integer> iterator = positions.iterator();
            if (iterator.hasNext()) {
                Integer position = iterator.next();
                Specs specs = mBuilder.mSpecsList.get(position);
                setSpecs(specs);
            } else {
                mBinding.tvGoodsSpec.setText(mBuilder.mSpecTips);
            }
            mBinding.viewCount.setCount(0);
        });

        setSpecs(mBuilder.mSpec);
        mBinding.viewCount.setCount(mBuilder.count);
        mBinding.viewCount.setMaxCount(mBuilder.maxCount);
        mBinding.viewCount.setMinCount(mBuilder.minCount);

        mBinding.btnAddCart.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mListener != null) {
                    mListener.onAddCart(mBuilder.mSpec, mBinding.viewCount.getCount());
                }
            }
        });
        mBinding.btnBuy.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mListener != null) {
                    mListener.onBuy(mBuilder.mSpec, mBinding.viewCount.getCount());
                }
            }
        });
        setTypeStatus();
    }

    private void setTypeStatus() {
        mBinding.btnAddCart.setVisibility(mBuilder.mType == Type.BUY ? View.GONE : View.VISIBLE);
        mBinding.btnBuy.setVisibility(mBuilder.mType == Type.CART ? View.GONE : View.VISIBLE);
    }

    private void setSpecs(Specs specs) {
        this.mBuilder.mSpec = specs;
        if (specs != null) {
            mBinding.tvGoodsSpec.setText(TextUtils.isEmpty(specs.spec_1) ? mBuilder.mSpecTips : specs.spec_1);
            mBinding.tvGoodsPrice.setText(PriceUtils.getPrice(specs.price));
        }
    }

    public void setOnClickGoodsSpecListener(OnClickGoodsSpecListener listener) {
        mListener = listener;
    }

    public static class Builder {

        private Type mType = Type.SPEC;
        private int count = 1;
        private int minCount = 1;
        private int maxCount;
        private String iconUrl;
        private String mSpecTips = DEFAULT_SPEC_TIPS;

        private Specs mSpec;
        private List<Specs> mSpecsList = new ArrayList<>();

        public Type getType() {
            return mType;
        }

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public int getCount() {
            return count;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public int getMinCount() {
            return minCount;
        }

        public Builder setMinCount(int minCount) {
            this.minCount = minCount;
            return this;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public Builder setMaxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Specs getSpec() {
            return mSpec;
        }


        public Builder setSpec(Specs spec) {
            mSpec = spec;
            return this;
        }

        public Builder setSpecList(List<Specs> specList) {
            mSpecsList.clear();
            if (specList != null) {
                mSpecsList.addAll(specList);
            }
            return this;
        }


        public GoodsSpecDialog build() {
            return new GoodsSpecDialog(this);
        }

        public String getSpecTips() {
            return mSpecTips;
        }

        public Builder setSpecTips(String specTips) {
            mSpecTips = specTips;
            return this;
        }

        public Builder setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }
    }


    @Override
    public void dismiss() {
        if (mListener != null) {
            mListener.onDismiss(mBuilder.mSpec, mBinding.viewCount.getCount());
        }
        super.dismiss();
    }

    public enum Type {
        SPEC, CART, BUY
    }

    public interface OnClickGoodsSpecListener {

        void onAddCart(Specs spec, int count);

        void onBuy(Specs spec, int count);

        void onDismiss(Specs spec, int count);
    }

}
