package com.asia.paint.biz.mine.money.recharge;

import android.text.TextUtils;

import com.asia.paint.android.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class RechargeTypeAdapter extends BaseGlideAdapter<RechargeType> {

    private RechargeType mCheckRechargeType;

    public RechargeTypeAdapter(@Nullable List<RechargeType> data) {
        super(R.layout.item_recharge_type, data);
    }

    public RechargeTypeAdapter(@Nullable List<RechargeType> data, RechargeType checkRechargeType) {
        super(R.layout.item_recharge_type, data);
        mCheckRechargeType = checkRechargeType;
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, RechargeType type) {
        if (type != null) {
            helper.setImageResource(R.id.iv_icon, type.iconId);
            helper.setText(R.id.tv_name, type.name);
            helper.setGone(R.id.tv_description, !TextUtils.isEmpty(type.description));
            helper.setText(R.id.tv_description, type.description);
            CheckBox checkBox = helper.getView(R.id.cb_check);
            checkBox.setChecked(type == mCheckRechargeType);
            checkBox.setListener(new CheckBox.OnCheckChangeListener() {
                @Override
                public void onChange(boolean isChecked) {

                }

                @Override
                public boolean changeBySelf() {
                    setCheckRechargeType(type);
                    return false;
                }
            });
            helper.setVisible(R.id.view_divider, helper.getLayoutPosition() != getItemCount() - 1);
        }
    }

    public RechargeType getCheckRechargeType() {
        return mCheckRechargeType;
    }

    public void setCheckRechargeType(RechargeType checkRechargeType) {
        mCheckRechargeType = checkRechargeType;
        notifyDataSetChanged();
    }
}
