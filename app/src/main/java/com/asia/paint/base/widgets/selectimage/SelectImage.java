package com.asia.paint.base.widgets.selectimage;

import android.content.Context;
import android.util.AttributeSet;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.databinding.ViewSelectImageBinding;
import com.asia.paint.utils.callback.OnChangeCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class SelectImage extends BaseFrameLayout<ViewSelectImageBinding> {

    private int mMaxShowImgCount;
    private SelectImageAdapter mSelectImageAdapter;
    private List<ImageMultiItemEntity> mEntities;
    private List<String> mUrls;

    public SelectImage(@NonNull Context context) {
        super(context);
    }

    public SelectImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mMaxShowImgCount = 9;
        mEntities = new ArrayList<>();
        mUrls = new ArrayList<>();
        mBinding.rvSelectImage.setLayoutManager(new GridLayoutManager(mContext, 4));
        mBinding.rvSelectImage.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 12));
        mSelectImageAdapter = new SelectImageAdapter();
        mSelectImageAdapter.setOnSelectImageListener(new SelectImageAdapter.OnSelectImageListener() {
            @Override
            public void onClickAddImage() {
                MatisseActivity.start(mContext, new OnChangeCallback<List<String>>() {
                    @Override
                    public void onChange(List<String> result) {
                        addImageUrls(result);
                    }
                });
            }

            @Override
            public void onClickAddVideo() {

            }

            @Override
            public void onClickImage(String url) {

            }

            @Override
            public void onDelImage(String url) {

            }
        });
        mBinding.rvSelectImage.setAdapter(mSelectImageAdapter);
        setImageUrls(new ArrayList<>());
    }

    public void addImageUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        urls.addAll(mUrls);
        setImageUrls(urls);
    }


    public void setImageUrls(List<String> urls) {

        mEntities.clear();
        mUrls.clear();
        if (urls != null) {
            mUrls.addAll(urls);
            for (String url : urls) {
                createImageEntity(url);
            }
        }
        if (showAddImage()) {
            createAddImage();
        }
        if (showAddVideo()) {
            createAddVideo();
        }
        mSelectImageAdapter.replaceData(mEntities);
    }

    public List<String> getUrls() {
        return mUrls;
    }

    private boolean showAddImage() {
        return mUrls.size() < mMaxShowImgCount;
    }

    private boolean showAddVideo() {
        return mUrls.size() < mMaxShowImgCount;
    }

    private void createImageEntity(String url) {
        ImageMultiItemEntity imgEntity = new ImageMultiItemEntity();
        imgEntity.setType(ImageMultiItemEntity.IMG);
        imgEntity.url = url;
        mEntities.add(imgEntity);
    }

    private void createAddVideo() {
        ImageMultiItemEntity addVideo = new ImageMultiItemEntity();
        addVideo.setType(ImageMultiItemEntity.ADD_VIDEO);
        mEntities.add(addVideo);
    }

    private void createAddImage() {
        ImageMultiItemEntity addImg = new ImageMultiItemEntity();
        addImg.setType(ImageMultiItemEntity.ADD_IMG);
        mEntities.add(addImg);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.view_select_image;
    }

}
