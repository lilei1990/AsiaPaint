package com.asia.paint.biz.mine.settings.address;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Address;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.utils.callback.OnChangePairCallback;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AddressAdapter extends BaseGlideAdapter<Address> {

    private OnChangePairCallback<Address, Integer> mChangeCallback;

    public AddressAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Address address) {
        if (address != null) {
            helper.setText(R.id.tv_name, address.consignee);
            helper.setText(R.id.tv_tel, address.tel);
            helper.setText(R.id.tv_address, (address.address + address.address_name).replace(" ", ""));
            CheckBox checkBox = helper.getView(R.id.cb_default);
            checkBox.setChecked(address.isDefault());
            checkBox.setListener(new CheckBox.OnCheckChangeListener() {
                @Override
                public void onChange(boolean isChecked) {
                    if (mChangeCallback != null && !isChecked) {
                        mChangeCallback.onChange(address, helper.getLayoutPosition());
                    }
                }

                @Override
                public boolean changeBySelf() {
                    return false;
                }
            });
            helper.addOnClickListener(R.id.tv_edit, R.id.tv_del);
        }
    }

    public void updateDefault(Address address) {
        List<Address> addresses = getData();
        for (Address addr : addresses) {
            if (addr != address) {
                addr.is_default = 0;
            }
        }
        notifyDataSetChanged();
    }

    public void setOnClickDefaultListener(OnChangePairCallback<Address, Integer> changeCallback) {
        mChangeCallback = changeCallback;
    }
}
