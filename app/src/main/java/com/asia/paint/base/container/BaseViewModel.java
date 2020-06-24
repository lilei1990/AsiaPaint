package com.asia.paint.base.container;

import androidx.lifecycle.ViewModel;

import com.asia.paint.base.network.bean.BaseListRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.BaseGlideMultiAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class BaseViewModel extends ViewModel {

	private int mCurPage = 1;

	private CompositeDisposable mDisposable = new CompositeDisposable();

	@Override
	protected void onCleared() {
		mDisposable.dispose();
	}

	public void addDisposable(Disposable disposable) {
		mDisposable.add(disposable);
	}

	public void onClear() {
		onCleared();
	}

	public <T> void updateListData(BaseGlideAdapter<T> adapter, BaseListRsp<T> result) {
		if (result != null) {
			List<T> data = result.data;
			if (data == null) {
				data = new ArrayList<>();
			}
			if (mCurPage > 1) {
				adapter.addData(data);
			} else {
				adapter.replaceData(data);
			}
			if (mCurPage < result.totalpage) {
				adapter.loadMoreComplete();
			} else {
				adapter.loadMoreEnd();
			}
		} else {
//			adapter.loadMoreFail();
			//TODO 如果后台返回数据结构不对，会导致问题，所以改成如下代码
			if (mCurPage == 1)
				adapter.replaceData(new ArrayList<>());
			adapter.loadMoreEnd();
		}
	}

	public <T> void updateListData(BaseGlideAdapter<T> adapter, List<T> result) {
		if (result != null) {
			List<T> data = result;
			if (data == null) {
				data = new ArrayList<>();
			}
			if (mCurPage > 1) {
				adapter.addData(data);
			} else {
				adapter.replaceData(data);
			}
			if (data != null && data.size() == 10) {
				adapter.loadMoreComplete();
			} else {
				adapter.loadMoreEnd();
			}
		} else {
//			adapter.loadMoreFail();
			//TODO 如果后台返回数据结构不对，会导致问题，所以改成如下代码
			if (mCurPage == 1)
				adapter.replaceData(new ArrayList<>());
			adapter.loadMoreEnd();
		}
	}

	public <T extends MultiItemEntity> void updateListData(BaseGlideMultiAdapter<T> adapter, BaseListRsp<T> result, boolean reverse) {
		if (result != null) {
			List<T> data = result.data;
			if (data == null) {
				data = new ArrayList<>();
			}
			if (mCurPage > 1) {
				if (reverse) {
					adapter.addData(0, data);
				} else {
					adapter.addData(data);
				}

			} else {
				adapter.replaceData(data);
			}
			if (mCurPage < result.totalpage) {
				adapter.loadMoreComplete();
			} else {
				adapter.loadMoreEnd();
			}
		} else {
//			adapter.loadMoreFail();
			//TODO 如果后台返回数据结构不对，会导致问题，所以改成如下代码
			if (mCurPage == 1)
				adapter.replaceData(new ArrayList<>());
			adapter.loadMoreEnd();
		}
	}

	public int getCurPage() {
		return mCurPage;
	}

	public void autoAddPage() {
		mCurPage++;
	}

	public void resetPage() {
		mCurPage = 1;
	}
}
