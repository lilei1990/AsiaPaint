package com.asia.paint.biz.comment;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CommentService;
import com.asia.paint.base.network.bean.CommentRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class CommentViewModel extends BaseViewModel {

    private CallbackDate<CommentRsp> mCommentRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mAddCommentRsp = new CallbackDate<>();

    public CallbackDate<CommentRsp> loadGoodsComment(int goods_id, int status) {
        NetworkFactory.createService(CommentService.class)
                .loadGoodsComment(goods_id, status, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CommentRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CommentRsp response) {
                        mCommentRsp.setData(response);
                    }
                });
        return mCommentRsp;
    }


    public CallbackDate<Boolean> addComment(int rec_id, String comment,
            float score, List<String> images, String video) {
        StringBuilder builder = new StringBuilder();
        if (images != null) {
            for (String url : images) {
                builder.append(url).append(",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        NetworkFactory.createService(CommentService.class)
                .addComment(rec_id, comment, score, builder.toString(), video, 0)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(true) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddCommentRsp.setData(true);
                    }
                });
        return mAddCommentRsp;
    }
}
