package com.asia.paint.biz.find.post.publish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.FileService;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.biz.find.post.PostViewModel;
import com.asia.paint.databinding.ActivityPublishPostBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PublishPostActivity extends BaseTitleActivity<ActivityPublishPostBinding> {

	private PublishPostAdapter mPublishPostAdapter;
	private PostViewModel mPostViewModel;

	@Override
	protected String middleTitle() {
		return "";
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_publish_post;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPostViewModel = getViewModel(PostViewModel.class);
		mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
		mBaseBinding.tvRightText.setText("发布");
		mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				publishPost();
			}
		});
		mBinding.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
		mBinding.rvImage.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
		mBinding.rvImage.setItemAnimator(null);
		mPublishPostAdapter = new PublishPostAdapter();
		mBinding.rvImage.setAdapter(mPublishPostAdapter);
		mPublishPostAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			int id = view.getId();
			if (id == R.id.iv_delete) {
				mPublishPostAdapter.getData().remove(mPublishPostAdapter.getData(position));
				addImageUrls(new ArrayList<>());
				mPublishPostAdapter.notifyDataSetChanged();
			} else if (id == R.id.iv_img && mPublishPostAdapter.isAddImg(view.getTag())) {
				MatisseActivity.start(PublishPostActivity.this, this::addImageUrls);
			}
		});
		mPublishPostAdapter.addImg(new ArrayList<>());
	}

	private void publishPost() {
		String content = mBinding.etContent.getText().toString().trim();
		List<String> img = mPublishPostAdapter.getImg();
		if (TextUtils.isEmpty(content) && (img == null || img.isEmpty())) {
			AppUtils.showMessage("请先写点什么吧~");
			return;
		}
		if (img != null && !img.isEmpty()) {

			FileUtils.uploadMultiFile(FileService.POSTER, img).setCallback(new OnChangeCallback<List<String>>() {
				@Override
				public void onChange(List<String> result) {
					Log.i("hongc", "onChange: " + result.toString());
					publish(content, result);
				}
			});
   /*         FileUtils.uploadMulti(FileService.POSTER, img).setCallback(result -> {
                List<String> urls = new ArrayList<>();
                if (result != null && !TextUtils.isEmpty(result.url)) {
                    urls.add(result.url);
                }
                publish(content, urls);
            });*/
		} else {
			publish(content, img);
		}
	}

	private void publish(String content, List<String> img) {
		mPostViewModel.publishPost(content, img).setCallback(result -> {
			if (result) {
				//TODO 发布成功，刷新买家秀和我的页面
				Intent intent = new Intent("android.intent.action.broadcastrefreshweibo");
				sendBroadcast(intent);
				finish();
			}
		});
	}

	private void addImageUrls(List<String> result) {
		mPublishPostAdapter.addImg(result);
	}
}
