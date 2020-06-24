package com.asia.paint.biz.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.CommentService;
import com.asia.paint.base.network.bean.CommentRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.databinding.ActivityCommentBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.DigitUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class CommentActivity extends BaseActivity<ActivityCommentBinding> {

    private static final String KEY_GOODS_ID = "KEY_GOODS_ID";
    private CommentViewModel mCommentViewModel;
    private int mGoodsId;
    private CommentAdapter mCommentAdapter;

    private int mCurType = -1;

    public static void start(Context context, int goodsId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(KEY_GOODS_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "商品评价";
    }


    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mGoodsId = intent.getIntExtra(KEY_GOODS_ID, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentViewModel = getViewModel(CommentViewModel.class);
        mBinding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvComment.addItemDecoration(new DefaultItemDecoration(0, 10, 0, 0));
        mCommentAdapter = new CommentAdapter();
        mCommentAdapter.setOnLoadMoreListener(() -> {
            mCommentViewModel.autoAddPage();
            loadGoodsComments(mCurType);
        }, mBinding.rvComment);
        mBinding.rvComment.setAdapter(mCommentAdapter);
        mBinding.tvAllComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mCommentViewModel.resetPage();
                loadGoodsComments(CommentService.ALL);
            }
        });
        mBinding.tvImgComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mCommentViewModel.resetPage();
                loadGoodsComments(CommentService.IMG);
            }
        });
        mBinding.tvNewComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mCommentViewModel.resetPage();
                loadGoodsComments(CommentService.NEW);
            }
        });
        mCommentViewModel.resetPage();
        loadGoodsComments(CommentService.ALL);

    }

    private void loadGoodsComments(int type) {
        mCurType = type;
        mCommentViewModel.loadGoodsComment(mGoodsId, mCurType).setCallback(this::updateComment);
        mBinding.tvAllComment.setSelected(type == CommentService.ALL);
        mBinding.tvImgComment.setSelected(type == CommentService.IMG);
        mBinding.tvNewComment.setSelected(type == CommentService.NEW);
    }

    private void updateComment(CommentRsp commentRsp) {
        setGoodsScore(DigitUtils.parseFloat(commentRsp.score));
        setCommentType(commentRsp.top);
        mCommentViewModel.updateListData(mCommentAdapter, commentRsp);
    }

    public void setGoodsScore(float score) {
        mBinding.ratingBarScore.setRating(score);
        mBinding.tvScore.setText(String.format(Locale.getDefault(), "%.1f", score));
    }

    public void setCommentType(List<Integer> commentCounts) {

        if (commentCounts == null || commentCounts.size() < 3) {
            return;
        }
        mBinding.tvAllComment.setText(String.format("全部(%s)", commentCounts.get(0)));
        mBinding.tvImgComment.setText(String.format("有图(%s)", commentCounts.get(1)));
        mBinding.tvNewComment.setText(String.format("最新(%s)", commentCounts.get(2)));
    }
}
