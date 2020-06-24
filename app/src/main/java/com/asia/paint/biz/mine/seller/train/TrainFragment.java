package com.asia.paint.biz.mine.seller.train;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Train;
import com.asia.paint.base.network.bean.TrainCategory;
import com.asia.paint.base.network.bean.TrainRsp;
import com.asia.paint.biz.mine.seller.train.detail.TrainDetailActivity;
import com.asia.paint.databinding.FragmentTrainBinding;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class TrainFragment extends BaseFragment<FragmentTrainBinding> {

    private static final String KEY_TRAIN_CATEGORY = "KEY_TRAIN_CATEGORY";

    private TrainViewModel mTrainViewModel;
    private TrainCategory mTrainCategory;
    private TrainAdapter mTrainAdapter;
    private String mKeyword;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_train;
    }

    public static TrainFragment createInstance(TrainCategory category) {
        TrainFragment fragment = new TrainFragment();
        if (category != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_TRAIN_CATEGORY, category);
            fragment.setArguments(bundle);
        }
        return fragment;
    }


    @Override
    protected void argumentsValue(Bundle bundle) {
        mTrainCategory = bundle.getParcelable(KEY_TRAIN_CATEGORY);
    }

    @Override
    protected void initView() {
        mTrainViewModel = getViewModel(TrainViewModel.class);
        mKeyword = "";
        mTrainViewModel.resetPage();
        mBinding.rvTrain.setLayoutManager(new LinearLayoutManager(mContext));
        mTrainAdapter = new TrainAdapter();
        mTrainAdapter.setOnLoadMoreListener(() -> {
            mTrainViewModel.autoAddPage();
            loadTrain();
        }, mBinding.rvTrain);
        mTrainAdapter.setOnItemClickListener((adapter, view, position) -> {
            Train data = mTrainAdapter.getData(position);
            if (data != null) {
                TrainDetailActivity.start(mContext, data.id);
            }
        });
        mBinding.rvTrain.setAdapter(mTrainAdapter);
        loadTrain();
    }

    public void search(String keyword) {
        if (mTrainViewModel == null) {
           return;
        }
        mKeyword = keyword;
        mTrainViewModel.resetPage();
        loadTrain();
    }

    private void loadTrain() {
        if (mTrainCategory != null) {
            mTrainViewModel.queryTrain(mTrainCategory.id, mKeyword).setCallback(this::updateTrain);
        }
    }

    private void updateTrain(TrainRsp trainRsp) {
        mTrainViewModel.updateListData(mTrainAdapter, trainRsp);
    }
}
