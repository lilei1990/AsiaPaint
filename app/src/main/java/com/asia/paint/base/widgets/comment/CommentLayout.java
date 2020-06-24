package com.asia.paint.base.widgets.comment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.comment.CommentActivity;
import com.asia.paint.databinding.ViewCommentLayoutBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author by chenhong14 on 2019-12-15.
 */
public class CommentLayout extends BaseFrameLayout<ViewCommentLayoutBinding> {

    private CommentImageAdapter mCommentImageAdapter;
    private int mGoodsId;

    public CommentLayout(@NonNull Context context) {
        super(context);
    }

    public CommentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_comment_layout;
    }


    @Override
    protected void initView() {
        mBinding.tvViewAllComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                CommentActivity.start(mContext, mGoodsId);
            }
        });
        mBinding.rvCommentImg.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mBinding.rvCommentImg.addItemDecoration(new DefaultItemDecoration(0, 0, 4, 0));
        mCommentImageAdapter = new CommentImageAdapter();
        mBinding.rvCommentImg.setAdapter(mCommentImageAdapter);
    }

    public void setGoodIs(int goodIs) {
        mGoodsId = goodIs;
    }

    public void setCommentCount(int count) {
        mBinding.tvCommentCount.setText(String.format("商品评价(%s)", count));
    }

    public void setCommentScore(float score) {
        mBinding.rbTotalScore.setRating(score);
        mBinding.tvScore.setText(String.valueOf(score));
    }

    public void setUserAvatar(String url) {
        ImageUtils.loadCircleImage(mBinding.ivCommentUserAvatar, url);
    }

    public void setUserName(String name) {
        mBinding.tvCommentUserName.setText(name);
    }

    public void setUserCommentScore(float score) {
        mBinding.rbUserScore.setRating(score);
    }


    public void setCommentTime(String time) {
        mBinding.tvCommentTime.setText(time);
    }

    public void setCommentContent(String content) {
        mBinding.tvCommentContent.setText(content);
    }

    public void addImage(List<String> urls) {
        if (urls == null) {
            urls = new ArrayList<>();
        }
        mCommentImageAdapter.replaceData(urls);
    }

    public void setUserVisibility(boolean show) {
        mBinding.layoutCommentUserInfo.setVisibility(show ? VISIBLE : GONE);
    }

}
