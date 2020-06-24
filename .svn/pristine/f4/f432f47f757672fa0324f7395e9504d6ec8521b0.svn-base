package com.asia.paint.biz.mine.settings.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.Address;
import com.asia.paint.base.network.bean.AddressRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.databinding.ActivityAddressBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AddressActivity extends BaseActivity<ActivityAddressBinding> {

    public static final int REQUEST_CODE_ORDER = 9999;
    public static final int REQUEST_CODE_SETTINGS = 8888;

    private AddressViewModel mViewModel;
    private AddressAdapter mAddressAdapter;

    private static final String KEY_REQUEST = "KEY_REQUEST";
    public static final String KEY_ADDRESS = "KEY_ADDRESS";

    private int mRequestCode;


    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AddressActivity.class);
        intent.putExtra(KEY_REQUEST, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mRequestCode = intent.getIntExtra(KEY_REQUEST, REQUEST_CODE_SETTINGS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "收货地址";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(AddressViewModel.class);
        mBinding.rvAddress.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvAddress.addItemDecoration(new DefaultItemDecoration(0, 12, 0, 0));
        mAddressAdapter = new AddressAdapter();
        mAddressAdapter.setOnClickDefaultListener((address, position) ->
                mViewModel.addDefaultAddress(address.address_id)
                        .setCallback(result -> {
                            address.is_default = 1;
                            mAddressAdapter.updateDefault(address);
                        }));
        mAddressAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            Address address = mAddressAdapter.getData().get(position);
            if (address == null) {
                return;
            }
            switch (id) {
                case R.id.tv_edit:
                    EditAddressActivity.start(AddressActivity.this, address);
                    break;
                case R.id.tv_del:
                    mViewModel.delAddress(address.address_id).setCallback(result -> mAddressAdapter.remove(position));
                    break;
            }
        });
        mAddressAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mRequestCode == REQUEST_CODE_ORDER) {
                Address address = mAddressAdapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra(KEY_ADDRESS, address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mBinding.rvAddress.setAdapter(mAddressAdapter);

        mBinding.tvAddAddress.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(AddressActivity.this, EditAddressActivity.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        queryAddress();
    }

    private void queryAddress() {
        mViewModel.queryAddress(1).setCallback(this::updateAddressRsp);
    }

    private void updateAddressRsp(AddressRsp addressRsp) {
        boolean isEmpty = addressRsp == null || addressRsp.address == null || addressRsp.address.isEmpty();
        mBinding.tvEmptyAddress.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        mBinding.rvAddress.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        if (!isEmpty) {
            mAddressAdapter.replaceData(addressRsp.address);
        }
    }
}
