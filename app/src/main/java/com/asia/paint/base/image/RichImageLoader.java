package com.asia.paint.base.image;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.asia.paint.banner.loader.ImageLoaderInterface;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-07.
 */
public class RichImageLoader implements ImageLoaderInterface<View> {

    static List<String> videoType = new ArrayList<>();

    static {
        videoType.add(".mp4");
    }


    @Override
    public void displayImage(Context context, Object url, View view) {
        if (isVideo(url)) {
            VideoView videoView = (VideoView) view;
            videoView.setVideoPath((String) url);
        } else {
            Glide.with(context).load(url).into((ImageView) view);
        }
    }

    @Override
    public View createImageView(Context context, Object url) {
        View view;
        if (isVideo(url)) {
            MediaController localMediaController = new MediaController(context);
            view = new VideoView(context);
            ((VideoView) view).setMediaController(localMediaController);
            view.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    ((VideoView) view).start();
                    Log.i("hongc", "onNoDoubleClick: ");
                }
            });
        } else {
            view = new ImageView(context);
        }
        return view;
    }

    private boolean isVideo(Object url) {
        if (url instanceof String) {
            int index = ((String) url).lastIndexOf(".");
            String flag = ((String) url).substring(index);
            return videoType.contains(flag);
        }
        return false;
    }
}
