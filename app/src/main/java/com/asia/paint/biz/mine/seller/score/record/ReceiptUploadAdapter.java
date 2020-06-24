package com.asia.paint.biz.mine.seller.score.record;

import com.asia.paint.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class ReceiptUploadAdapter extends BaseGlideAdapter<Object> {
    private int mShowPhotoCount = 9;
    private int mAddResId = R.mipmap.ic_post_add_img;

    public ReceiptUploadAdapter() {
        super(R.layout.item_receipt_upload);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Object url) {
        helper.loadImage(R.id.iv_img, url);
        helper.setTag(R.id.iv_img, url);
        helper.addOnClickListener(R.id.iv_delete, R.id.iv_img);
        helper.setGone(R.id.iv_delete, !isAddImg(url));
    }

    public boolean isAddImg(Object url) {
        if (url instanceof Integer) {
            int value = (int) url;
            return value == mAddResId;
        }
        return false;
    }

    public void addImg(List<String> urls) {
        addImg(urls, -1, R.mipmap.ic_post_add_img);
    }

    public void addImg(List<String> urls, int addResId) {
        addImg(urls, -1, addResId);
    }

    public void addImg(List<String> urls, int showPhotoCount, int addResId) {
        mAddResId = addResId;
        List<Object> data = new ArrayList<>(getData());
        if (data.contains(mAddResId)) {
            data.remove((Integer) mAddResId);
        }

        if (showPhotoCount > 0) {
            mShowPhotoCount = showPhotoCount;
        }

        if (urls != null) {
            int canAddCount = mShowPhotoCount - data.size();
            if (canAddCount < urls.size()) {
                urls = urls.subList(0, canAddCount);
            }
            data.addAll(urls);
        }
        if (data.size() < mShowPhotoCount) {
            data.add(mAddResId);
        }
        updateData(data, true);
    }

    public List<String> getImg() {
        List<Object> data = getData();
        List<String> urls = new ArrayList<>();
        for (Object obj : data) {
            if (obj instanceof String) {
                urls.add((String) obj);
            }
        }
        return urls;
    }
}
