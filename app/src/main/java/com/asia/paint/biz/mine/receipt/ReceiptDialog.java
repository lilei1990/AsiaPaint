package com.asia.paint.biz.mine.receipt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.disposables.Disposable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.network.bean.Receipt;
import com.asia.paint.base.network.bean.ReceiptRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.databinding.DialogOrderReceiptBinding;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.OnChangePairCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class ReceiptDialog extends BaseBottomDialogFragment<DialogOrderReceiptBinding> {


    private OnChangePairCallback<List<Receipt>, Receipt> mCallback;
    private ReceiptAdapter mReceiptAdapter;
    private List<Receipt> mReceipts = new ArrayList<>();
    private ReceiptViewModel mReceiptViewModel;
    private int page=1;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_order_receipt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiptViewModel = new ReceiptViewModel();
    }

    @Override
    protected void initView() {

        mBinding.rvOrderReceipt.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvOrderReceipt.addItemDecoration(new DefaultItemDecoration(0, 8, 0, 0));
        mBinding.rvOrderReceipt.setItemAnimator(null);
        mReceiptAdapter = new ReceiptAdapter(mContext);
        mReceiptAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Receipt receipt = mReceiptAdapter.getData(position);
            if (receipt == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.tv_edit_receipt:
                    ReceiptActivity.start((Activity) mContext, receipt, result -> {
//                        List<Receipt> receipts = mReceiptAdapter.getData();
//                        Receipt target = null;
//                        for (Receipt receipt1 : receipts) {
//                            if (receipt1 != null && receipt1.id == result.id) {
//                                target = receipt1;
//                                break;
//                            }
//                        }
//                        if (target != null) {
//                            int index = receipts.indexOf(target);
//                            receipts.set(index, result);
//                            mReceiptAdapter.notifyDataSetChanged();
//                        }
//                    });
                        mReceipts.clear();
                        loadReceipt(1);
                    });
                    break;
                case R.id.tv_del_receipt:
                    mReceiptViewModel.delReceipt(receipt.id).setCallback(result -> {
                        if (result) {
                            mReceiptAdapter.remove(position);
                            if (mCallback != null) {
                                mCallback.onChange(mReceiptAdapter.getData(), null);
                            }
                        }
                    });
                    break;
            }

        });
        mReceiptAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mCallback != null) {
                mCallback.onChange(mReceiptAdapter.getData(), mReceiptAdapter.getData(position));
            }
            dismiss();
        });
        mBinding.rvOrderReceipt.setAdapter(mReceiptAdapter);
        if (mReceipts != null) {
            mReceiptAdapter.replaceData(mReceipts);
        }
        mBinding.tvNot.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mCallback != null) {
                    mCallback.onChange(mReceiptAdapter.getData(), null);
                }
                dismiss();
            }
        });
        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.tvNewReceipt.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                ReceiptActivity.start((Activity) mContext, null, result -> {
//                    List<Receipt> receipts = mReceiptAdapter.getData();
//                    receipts.add(0, result);
//                    mReceiptAdapter.notifyDataSetChanged();
                    mReceipts.clear();
                    loadReceipt(1);
                });
            }
        });
    }

    public void setReceipts(List<Receipt> receipts) {
        mReceipts.clear();
        if (receipts != null) {
            mReceipts.addAll(receipts);
        }
        if (mReceiptAdapter != null) {
            mReceiptAdapter.replaceData(mReceipts);
        }
    }

    @Override
    protected int getDialogHeight() {
        return AppUtils.dp2px(416);
    }

    @Override
    public void onDestroy() {
        if (mReceiptViewModel != null) {
            mReceiptViewModel.onClear();
        }
        super.onDestroy();
    }

    public void setCallback(OnChangePairCallback<List<Receipt>, Receipt> callback) {
        mCallback = callback;
    }


    private void loadReceipt(int page) {
        mReceiptViewModel.loadReceipt(page).setCallback(result -> {
            if (result != null && result.data != null) {
                mReceipts.addAll(result.data);
                if (page < result.totalpage) {
                    loadReceipt(page + 1);
                } else {
                    if (mReceiptAdapter!=null) {
                        mReceiptAdapter.replaceData(mReceipts);
                        mReceiptAdapter.notifyDataSetChanged();
                        if (mCallback != null) {
                            mCallback.onChange(mReceiptAdapter.getData(), null);
                        }
                    }
                }
            }else {
                if (mReceiptAdapter!=null) {
                    mReceiptAdapter.replaceData(mReceipts);
                    mReceiptAdapter.notifyDataSetChanged();
                    if (mCallback != null) {
                        mCallback.onChange(mReceiptAdapter.getData(), null);
                    }
                }
            }
        });
    }

}
