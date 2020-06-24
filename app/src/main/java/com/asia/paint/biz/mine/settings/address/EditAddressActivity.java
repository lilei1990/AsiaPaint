package com.asia.paint.biz.mine.settings.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.Address;
import com.asia.paint.databinding.ActivityEditAddressBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class EditAddressActivity extends BaseActivity<ActivityEditAddressBinding> {

    private static final String KEY_ADDRESS = "KEY_ADDRESS";
    private StringBuilder mAreaBuilder;
    private AddressViewModel mAddressViewModel;
    private Address mAddress;

    public static void start(Context context, Address address) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        if (address != null) {
            intent.putExtra(KEY_ADDRESS, address);
        }
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mAddress = intent.getParcelableExtra(KEY_ADDRESS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "编辑地址";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddressViewModel = getViewModel(AddressViewModel.class);
        mAreaBuilder = new StringBuilder();
        mBaseBinding.tvRightText.setText("保存");
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                String name = mBinding.etName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    AppUtils.showMessage("请填写收货人");
                    return;
                }
                String tel = mBinding.etTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    AppUtils.showMessage("请填写手机号");
                    return;
                }
                String area = mAreaBuilder != null ? mAreaBuilder.toString() : "";
                if (TextUtils.isEmpty(area) && mAddress != null) {
                    area = mAddress.address;
                }
                if (TextUtils.isEmpty(area)) {
                    AppUtils.showMessage("请选择所在区域");
                    return;
                }
                String address = mBinding.etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    AppUtils.showMessage("请填写详细地址");
                    return;
                }
                if (mAddress != null) {
                    mAddressViewModel.editAddress(mAddress.address_id, name, tel, area, address)
                            .setCallback(result -> finish());
                } else {
                    mAddressViewModel.addAddress(name, tel, area, address)
                            .setCallback(result -> finish());
                }

            }
        });
        mBinding.tvPickAddress.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
           /*     BottomDialog dialog = new BottomDialog(EditAddressActivity.this);
                dialog.setDialogDismisListener(dialog::dismiss);
                dialog.setOnAddressSelectedListener((province, city, county, street) -> {
                    mAreaBuilder = new StringBuilder();
                    if (province != null) {
                        mAreaBuilder.append(province.name).append(" ");
                    }
                    if (city != null) {
                        mAreaBuilder.append(city.name).append(" ");
                    }
                    if (county != null) {
                        mAreaBuilder.append(county.name).append(" ");
                    }
                    if (street != null) {
                        mAreaBuilder.append(street.name);
                    }
                    mBinding.tvPickAddress.setText(mAreaBuilder.toString().replace(" ", ""));
                    dialog.dismiss();
                });
                dialog.show();*/
                showAddressDialog();
            }
        });
        update();
    }

    private void showAddressDialog() {
        JDCityPicker cityPicker = new JDCityPicker();
        JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();

        jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
        cityPicker.init(this);
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                mAreaBuilder = new StringBuilder();
                if (province != null) {
                    mAreaBuilder.append(province.getName()).append(" ");
                }
                if (city != null) {
                    mAreaBuilder.append(city.getName()).append(" ");
                }
                if (district != null) {
                    mAreaBuilder.append(district.getName()).append(" ");
                }
          /*      if (street != null) {
                    mAreaBuilder.append(street.name);
                }*/
                mBinding.tvPickAddress.setText(mAreaBuilder.toString().replace(" ", ""));
            }

            @Override
            public void onCancel() {
            }
        });
        cityPicker.showCityPicker();
    }

    public void update() {
        if (mAddress != null) {
            mBinding.etName.setText(mAddress.consignee);
            mBinding.etTel.setText(mAddress.tel);
            if (!TextUtils.isEmpty(mAddress.address)) {
                mBinding.tvPickAddress.setText(mAddress.address.replace(" ", ""));
            }
            mBinding.etAddress.setText(mAddress.address_name);

        }
    }
}
