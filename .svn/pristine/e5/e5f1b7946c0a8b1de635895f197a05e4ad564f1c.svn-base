package com.asia.paint.biz.zone;

import android.text.TextUtils;

import com.asia.paint.R;
import com.asia.paint.banner.Banner;
import com.asia.paint.banner.BannerConfig;
import com.asia.paint.base.image.GlideImageLoader;
import com.asia.paint.base.network.bean.ColorZone;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class ZoneAdapter extends BaseGlideAdapter<ColorZone> {
    public ZoneAdapter() {
        super(R.layout.item_zone);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, ColorZone colorZone) {
        if (colorZone != null) {
            helper.loadImage(R.id.iv_cover, colorZone.cover);
            if (!TextUtils.isEmpty(colorZone.name)) {
                helper.setText(R.id.tv_zone_name, String.format("— %s —", colorZone.name));
            }

            Banner banner = helper.getView(R.id.view_banner);
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            banner.releaseBanner();
            if (colorZone.images != null) {
                banner.update(colorZone.images);
            }
        }
    }
}
