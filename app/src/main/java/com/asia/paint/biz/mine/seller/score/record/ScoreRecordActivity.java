package com.asia.paint.biz.mine.seller.score.record;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.CashRecord;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.biz.mine.settings.account.CashAccountViewModel;
import com.asia.paint.databinding.ActivityScoreRecordBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class ScoreRecordActivity extends BaseTitleActivity<ActivityScoreRecordBinding> {

    private ScoreRecordAdapter mScoreRecordAdapter;
    private CashAccountViewModel mViewModel;

    @Override
    protected String middleTitle() {
        return "申请记录";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score_record;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(CashAccountViewModel.class);
        mViewModel.resetPage();
        mBinding.rvScoreRecord.setLayoutManager(new LinearLayoutManager(this));
        mScoreRecordAdapter = new ScoreRecordAdapter();
        mScoreRecordAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadScoreRecord();
        }, mBinding.rvScoreRecord);
        mScoreRecordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CashRecord record = mScoreRecordAdapter.getData(position);
                int id = view.getId();
                if (id == R.id.tv_receipt) {
                    ReceiptTypeDialog dialog = new ReceiptTypeDialog();
                    dialog.setChangeCallback(new OnChangeCallback<Integer>() {
                        @Override
                        public void onChange(Integer receiptId) {
                            ReceiptUploadDialog uploadDialog = ReceiptUploadDialog.createInstance(receiptId);
                            uploadDialog.setOnReceiptDialogListener(new ReceiptUploadDialog.OnReceiptDialogListener() {
                                @Override
                                public void onChange(String sn, String receiptMoney, List<String> images) {
                                    if (images != null && !images.isEmpty()) {
                                        FileUtils.uploadMultiFile(FileService.SELLER, images).setCallback(new OnChangeCallback<List<String>>() {
                                            @Override
                                            public void onChange(List<String> result) {
                                                uploadReceipt(result, record, receiptId, receiptMoney, sn);
                                            }
                                        });
                                    } else {
                                        uploadReceipt(null, record, receiptId, receiptMoney, sn);
                                    }
                                }
                            });
                            uploadDialog.show(ScoreRecordActivity.this);
                        }
                    });
                    dialog.show(ScoreRecordActivity.this);
                }
            }
        });
        mBinding.rvScoreRecord.setAdapter(mScoreRecordAdapter);
        loadScoreRecord();
    }

    private void uploadReceipt(List<String> result, CashRecord record, Integer receiptId, String receiptMoney, String sn) {
        mViewModel.uploadReceipt(record.id, receiptId, receiptMoney, sn, result).setCallback(result1 -> {
            if (result1) {
                mViewModel.resetPage();
                loadScoreRecord();
            }
        });
    }

    private void loadScoreRecord() {
        mViewModel.cashRecord().setCallback(result -> mViewModel.updateListData(mScoreRecordAdapter, result));
    }

}
