package com.asia.paint.biz.mine.favorites;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.model.CollectViewModel;
import com.asia.paint.base.network.bean.FavoritesRsp;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.databinding.ActivityFavoritesBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class FavoritesActivity extends BaseActivity<ActivityFavoritesBinding> {

	private FavoritesAdapter mFavoritesAdapter;
	private CollectViewModel mCollectViewModel;
	private boolean isCheckAll = false;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_favorites;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "我的收藏";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCollectViewModel = getViewModel(CollectViewModel.class);
		mCollectViewModel.resetPage();
		mBinding.rvFavorGoods.setLayoutManager(new LinearLayoutManager(this));
		mFavoritesAdapter = new FavoritesAdapter();
		mFavoritesAdapter.setOnLoadMoreListener(() -> {
			mCollectViewModel.autoAddPage();
			loadCollect();
		}, mBinding.rvFavorGoods);
		mBinding.rvFavorGoods.setAdapter(mFavoritesAdapter);

		//编辑按钮
		mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mBaseBinding.tvRightText.getText().equals("编辑")) {
					mBaseBinding.tvRightText.setText("完成");
					mFavoritesAdapter.isShowCheckBox = true;
					mFavoritesAdapter.notifyDataSetChanged();
					mBinding.llEdit.setVisibility(View.VISIBLE);
				} else if (mBaseBinding.tvRightText.getText().equals("完成")) {
					mBaseBinding.tvRightText.setText("编辑");
					mFavoritesAdapter.isShowCheckBox = false;
					mFavoritesAdapter.notifyDataSetChanged();
					mBinding.llEdit.setVisibility(View.GONE);
				}
			}
		});

		mFavoritesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				if (mFavoritesAdapter.isShowCheckBox) {
					mFavoritesAdapter.getData(position).isSelected = !mFavoritesAdapter.getData(position).isSelected;
					mFavoritesAdapter.notifyDataSetChanged();
				} else {
					GoodsDetailActivity.start(FavoritesActivity.this, mFavoritesAdapter.getData(position).goods_id);
				}
			}
		});
		mBinding.ivCheckAll.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				checkAll();
			}
		});
		mBinding.tvCheckAll.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				checkAll();
			}
		});
		mBinding.btnRemove.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mFavoritesAdapter.getSelectedCount() > 0) {
					new MessageDialog.Builder()
							.title("确认要从收藏中移除选中的商品吗？")
							.setCancelButton("取消", null)
							.setSureButton("确认", new OnNoDoubleClickListener() {
								@Override
								public void onNoDoubleClick(View view) {
									mCollectViewModel.delCollect(mFavoritesAdapter.getSelectedId()).setCallback(result -> {
										mFavoritesAdapter.isShowCheckBox = false;
										mBaseBinding.tvRightText.performClick();
										mCollectViewModel.resetPage();
										loadCollect();
									});
								}
							}).build()
							.show(FavoritesActivity.this);
				} else {
					ToastUtils.showShortToast(getApplicationContext(), "请选择要删除的商品");
				}
			}
		});
		loadCollect();
	}

	/**
	 * 全选
	 */
	private void checkAll() {
		isCheckAll = !isCheckAll;
		if (isCheckAll) {
			mBinding.ivCheckAll.setImageResource(R.mipmap.ic_checkbox_selected);
		} else {
			mBinding.ivCheckAll.setImageResource(R.mipmap.ic_checkbox_normal);
		}
		for (FavoritesRsp.Favorite item : mFavoritesAdapter.getData()) {
			item.isSelected = isCheckAll;
		}
		mFavoritesAdapter.notifyDataSetChanged();
	}

	private void loadCollect() {
		mCollectViewModel.queryCollect().setCallback(result -> {
			mCollectViewModel.updateListData(mFavoritesAdapter, result);
			if (result != null) {
				updateGoods(result.data);
			} else {
				updateGoods(null);
			}
		});
	}

	public void updateGoods(List<FavoritesRsp.Favorite> goods) {
		boolean isEmpty = goods == null || goods.isEmpty();
		mBinding.tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
		mBinding.rvFavorGoods.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
		mBaseBinding.tvRightText.setText(isEmpty ? "" : "编辑");
	}
}
