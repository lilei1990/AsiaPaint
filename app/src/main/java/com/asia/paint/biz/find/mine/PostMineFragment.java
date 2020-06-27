package com.asia.paint.biz.find.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.network.bean.PostRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.find.post.PostAdapter;
import com.asia.paint.biz.find.post.PostViewModel;
import com.asia.paint.biz.find.post.detail.PostDetailActivity;
import com.asia.paint.biz.find.post.publish.PublishPostActivity;
import com.asia.paint.databinding.FragmentPostmineBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Administrator on 2020/6/27.
 */

public class PostMineFragment extends BaseFragment<FragmentPostmineBinding>implements OnChangeCallback<PostRsp> {

    public static final int TYPE_MY_POST = 2;
    public static final int TYPE_FOLLOW_POST = 3;
    private static final String KEY_POST_TYPE = "KEY_POST_TYPE";
    private PostViewModel mPostViewModel;
    private PostAdapter mPostAdapter;
    private int mType;
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

    public static PostMineFragment createInstance(int type) {
        PostMineFragment postFragment = new PostMineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POST_TYPE, type);
        postFragment.setArguments(bundle);
        return postFragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_POST_TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_postmine;
    }

    @Override
    protected void initView() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.broadcastrefreshweibo");
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

        mPostViewModel = getViewModel(PostViewModel.class);
        mPostViewModel.resetPage();
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
        //如果是关注，刷新一下
        if (mType == TYPE_FOLLOW_POST) {
            mPostViewModel.resetPage();
            mPostViewModel.loadFollowPost(mPostViewModel.getCurPage()).setCallback(this);
        }
    }

    private void loadPost() {
        switch (mType) {
            case TYPE_MY_POST:
                mPostViewModel.loadMyPost(mPostViewModel.getCurPage()).setCallback(this);
                break;
            case TYPE_FOLLOW_POST:
                mPostViewModel.loadFollowPost(mPostViewModel.getCurPage()).setCallback(this);
                break;
        }
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