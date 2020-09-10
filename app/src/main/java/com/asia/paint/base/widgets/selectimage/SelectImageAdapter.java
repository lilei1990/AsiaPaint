package com.asia.paint.base.widgets.selectimage;

import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.base.recyclerview.BaseGlideMultiAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-27.
 */
public class SelectImageAdapter extends BaseGlideMultiAdapter<ImageMultiItemEntity> {

    private OnSelectImageListener mImageListener;

    public SelectImageAdapter() {
        super();
        addItemType(ImageMultiItemEntity.IMG, R.layout.item_select_image);
        addItemType(ImageMultiItemEntity.ADD_IMG, R.layout.item_add_image);
        addItemType(ImageMultiItemEntity.ADD_VIDEO, R.layout.item_add_video);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, ImageMultiItemEntity item) {
        switch (item.getItemType()) {
            case ImageMultiItemEntity.ADD_IMG:
                helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View view) {
                        if (mImageListener != null) {
                            mImageListener.onClickAddImage();
                        }
                    }
                });
                break;
            case ImageMultiItemEntity.ADD_VIDEO:
                helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View view) {
                        if (mImageListener != null) {
                            mImageListener.onClickAddVideo();
                        }
                    }
                });
                break;
            case ImageMultiItemEntity.IMG:
                helper.loadRoundedCornersImage(R.id.iv_icon, item.url, 4);
                helper.setOnClickListener(R.id.iv_icon, new OnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View view) {
                        if (mImageListener != null) {
                            mImageListener.onClickImage(item.url);
                        }
                    }
                });
                helper.setOnClickListener(R.id.iv_delete, new OnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View view) {
                        if (mImageListener != null) {
                            mImageListener.onDelImage(item.url);
                        }
                    }
                });
                break;
        }
    }

    public void setOnSelectImageListener(OnSelectImageListener imageListener) {
        mImageListener = imageListener;
    }

    public interface OnSelectImageListener {
        void onClickAddImage();

        void onClickAddVideo();

        void onClickImage(String url);

        void onDelImage(String url);
    }
}
