package com.asia.paint.biz.comment.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.comment.CommentViewModel;
import com.asia.paint.databinding.ActivityAddCommentBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-27.
 */
public class AddCommentActivity extends BaseActivity<ActivityAddCommentBinding> {

    private static final String KEY_GOODS_COMMENT = "KEY_GOODS_IMAGE";
    private CommentViewModel mViewModel;
    private OrderDetail.Goods mGoods;

    public static void start(Context context, OrderDetail.Goods goods) {
        Intent intent = new Intent(context, AddCommentActivity.class);
        intent.putExtra(KEY_GOODS_COMMENT, goods);
        context.startActivity(intent);
    }


    @Override
    protected void intentValue(Intent intent) {
        mGoods = intent.getParcelableExtra(KEY_GOODS_COMMENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "发表评价";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(CommentViewModel.class);
        if (mGoods != null) {
            ImageUtils.loadRoundedCornersImage(mBinding.ivGoodsImg, mGoods.goods_image, 4);
            mBinding.tvGoodsName.setText(mGoods.goods_name);
        }
        mBinding.tvAddComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                float score = mBinding.ratingBarScore.getRating();
                String comment = getText(mBinding.etAddComment);
                List<String> urls = mBinding.addImgAndVideo.getUrls();
                FileUtils.uploadMultiFile(FileService.COMMENT, urls).setCallback(result ->
                        addComment(result, comment, score));
            }
        });
    }

    private void addComment(List<String> images, String comment, float score) {
        if (mGoods != null) {
            mViewModel.addComment(mGoods.rec_id, comment, score, images, null)
                    .setCallback(result -> {
                        if (result) {
                            finish();
                        }
                    });
        }
    }
}
