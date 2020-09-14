package com.asia.paint.biz.mine.index;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.SellerService;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.api.UserService;
import com.asia.paint.base.network.bean.ArticleDataRsp;
import com.asia.paint.base.network.bean.GetUserPost;
import com.asia.paint.base.network.bean.IndexBaseRsp;
import com.asia.paint.base.network.bean.MineDataRsp;
import com.asia.paint.base.network.bean.RechargeDetailRsp;
import com.asia.paint.base.network.bean.SellerInfoRsp;
import com.asia.paint.base.network.bean.UserDetail;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.base.network.core.DefaultNetworkObserverList;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.CallbackDateList;
import com.asia.paint.utils.utils.AppUtils;
import com.smarttop.library.utils.LogUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-28.
 */
public class MineViewModel extends BaseViewModel {

	private CallbackDateList<GetUserPost> mGetUserPostRsp = new CallbackDateList<>();
	private CallbackDate<ArticleDataRsp> mArticleDataResult = new CallbackDate<>();
	private CallbackDate<MineDataRsp> mMineDataResult = new CallbackDate<>();
	private CallbackDate<UserDetail> mUserInfoResult = new CallbackDate<>();
	private CallbackDate<SellerInfoRsp> mSellerInfoRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mApplySellerRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mEditUserRsp = new CallbackDate<>();
	private CallbackDate<RechargeDetailRsp> mMoneyRsp = new CallbackDate<>();
	private CallbackDate<IndexBaseRsp> mIndexBaseRsp = new CallbackDate<>();

	public CallbackDateList<GetUserPost> loadGetuserpost() {
		NetworkFactory.createService(UserService.class)
				.loadGetuserpost()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserverList<GetUserPost>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(List<GetUserPost> response) {
						mGetUserPostRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						LogUtil.e("拼团：", e.getMessage());
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mGetUserPostRsp;
	}

	public CallbackDate<ArticleDataRsp> loadArticleData() {
		NetworkFactory.createService(UserService.class)
				.loadArticleData("分销说明")
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ArticleDataRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ArticleDataRsp response) {
						mArticleDataResult.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mArticleDataResult.setData(null);
					}
				});
		return mArticleDataResult;
	}

	public CallbackDate<MineDataRsp> loadMineData() {
		NetworkFactory.createService(UserService.class)
				.loadMineData()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<MineDataRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(MineDataRsp response) {
						mMineDataResult.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						LogUtil.e("loadMineData exception", e.toString());
						super.onError(e);
						mMineDataResult.setData(null);
					}
				});
		return mMineDataResult;
	}

	public CallbackDate<UserDetail> loadUserInfoDetail() {
		NetworkFactory.createService(UserService.class)
				.loadUserInfoDetail()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<UserDetail>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(UserDetail response) {
						mUserInfoResult.setData(response);
					}

				});
		return mUserInfoResult;
	}

	public CallbackDate<Boolean> editUserInfo(String nickname, int sex, String birthday, String avatar) {
		if (avatar != null && avatar.contains(Constants.URL_HEAD)) {
			avatar = avatar.replace(Constants.URL_HEAD, "");
		}
		NetworkFactory.createService(UserService.class)
				.editUserInfo(nickname, sex, birthday, avatar)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(true) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mEditUserRsp.setData(true);
					}

				});
		return mEditUserRsp;
	}

	public CallbackDate<RechargeDetailRsp> queryMoneyDetail() {
		NetworkFactory.createService(UserService.class)
				.queryMoneyDetail(getCurPage())
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<RechargeDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(RechargeDetailRsp response) {
						mMoneyRsp.setData(response);
					}

				});
		return mMoneyRsp;
	}

	public CallbackDate<SellerInfoRsp> loadSellerInfo() {
		NetworkFactory.createService(SellerService.class)
				.loadSellerInfo()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<SellerInfoRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(SellerInfoRsp response) {
						mSellerInfoRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						LogUtil.e("loadSellerInfo exception", e.toString());
					}
				});
		return mSellerInfoRsp;
	}

	public CallbackDate<UserDetail> loadSellerInfoDetail() {
		NetworkFactory.createService(SellerService.class)
				.loadSellerInfoDetail()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<UserDetail>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(UserDetail response) {
						mUserInfoResult.setData(response);
					}

				});
		return mUserInfoResult;
	}

	public CallbackDate<Boolean> applySeller(String address, String address_name, String idCard, String name, String post) {
		NetworkFactory.createService(SellerService.class)
				.applySeller(address, address_name, idCard, name, post)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mApplySellerRsp.setData(true);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
					}
				});
		return mApplySellerRsp;
	}

	public CallbackDate<IndexBaseRsp> loadIndexBase() {
		NetworkFactory.createService(ShopService.class)
				.loadIndexBase()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<IndexBaseRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(IndexBaseRsp response) {
						mIndexBaseRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mIndexBaseRsp;
	}
}