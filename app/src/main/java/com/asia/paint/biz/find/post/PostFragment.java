package com.asia.paint.biz.find.post;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentPostBinding;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.network.bean.PostRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.find.post.detail.PostDetailActivity;
import com.asia.paint.biz.find.post.publish.PublishPostActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class PostFragment extends BaseFragment<FragmentPostBinding> implements OnChangeCallback<PostRsp> {

    private PostViewModel mPostViewModel;
    private PostAdapter mPostAdapter;
    private IntentFilter intentFilter;
    private MyReceiver myReceiver;

    /**
     * 刷新
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mPostViewModel.resetPage();
            loadPost();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post;
    }

    @Override
    protected void initView() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.broadcastrefreshweibo");
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

        mPostViewModel = getViewModel(PostViewModel.class);
        mPostViewModel.resetPage();
        mBinding.ivPublishPost.setVisibility( View.VISIBLE );
        mBinding.ivPublishPost.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(mContext, PublishPostActivity.class));
            }
        });
        mBinding.rvPost.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvPost.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mBinding.rvPost.setItemAnimator(null);
        mPostAdapter = new PostAdapter();
        mPostAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Post post = mPostAdapter.getData(position);
            if (post != null && view.getId() == R.id.tv_post_like) {
                mPostViewModel.likePost(post.id).setCallback(result -> {
                    post.reversePraise();
                    mPostAdapter.notifyItemChanged(position);
                });
            }
        });
        mPostAdapter.setOnItemClickListener((adapter, view, position) -> {
            Post post = mPostAdapter.getData(position);
            if (post != null) {
                PostDetailActivity.start(mContext, post.id);
            }
        });
        mBinding.rvPost.setAdapter(mPostAdapter);
        mPostAdapter.setOnLoadMoreListener(() -> {
            mPostViewModel.autoAddPage();
            loadPost();
        }, mBinding.rvPost);
        loadPost();

    }

    /*@Override
    public void onStart() {
        super.onStart();
        if (mType == TYPE_ALL_POST){
            loadPost();
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadPost() {
        mPostViewModel.loadPost(mPostViewModel.getCurPage()).setCallback(this);
    }

    @Override
    public void onChange(PostRsp postRsp) {
        mPostViewModel.updateListData(mPostAdapter, postRsp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
    }
}
