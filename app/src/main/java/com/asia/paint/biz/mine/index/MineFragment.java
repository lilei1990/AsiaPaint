package com.asia.paint.biz.mine.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.acker.simplezxing.activity.CaptureActivity;
import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentMineBinding;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.image.GlideImageLoader;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Banner;
import com.asia.paint.base.network.bean.MineDataRsp;
import com.asia.paint.base.network.bean.SellerInfoRsp;
import com.asia.paint.base.network.bean.UserDetail;
import com.asia.paint.base.util.FileUtils;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.base.util.WeiXinUtils;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.login.LoginActivity;
import com.asia.paint.biz.mine.favorites.FavoritesActivity;
import com.asia.paint.biz.mine.message.MessageActivity;
import com.asia.paint.biz.mine.seller.monthly.detail.MonthlyDetailActivity;
import com.asia.paint.biz.mine.seller.recommend.RecommendCodeActivity;
import com.asia.paint.biz.mine.seller.store.MyStoreCodeDialog;
import com.asia.paint.biz.mine.settings.SettingsActivity;
import com.asia.paint.biz.mine.user.EditUserActivity;
import com.asia.paint.biz.mine.vip.ApplyForVipActivity;
import com.asia.paint.biz.order.mine.MyPinTuanActivity;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.QrCodeUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-11.
 */
public class MineFragment extends BaseFragment<FragmentMineBinding> {


	private MineViewModel mMineViewModel;
	/**
	 * 亚士号
	 */
	private String asiaCode = "";

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_mine;
	}

	@Override
	protected void initView() {
		mMineViewModel = getViewModel(MineViewModel.class);

		mBinding.ivMineSetting.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, SettingsActivity.class));
			}
		});
		mBinding.layoutMsg.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, MessageActivity.class));
			}
		});
		mBinding.ivEditName.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, EditUserActivity.class));
			}
		});
		mBinding.ivAvatar.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, EditUserActivity.class));
			}
		});
		//我的店铺码
		mBinding.tvMineMyStoreCode.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				MyStoreCodeDialog dialog = MyStoreCodeDialog.createInstance(asiaCode);
				dialog.show(mContext);
				dialog.setChangeCallback(result -> {
					shareToWeiXin(Constants.URL + "?sellerId=" + asiaCode);
				});
			}
		});
		//我的拼团
		mBinding.cvMinePintuan.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				MyPinTuanActivity.start(getContext(), OrderService.PINTUAN_ALL);
			}
		});
		//邀请他人注册分销商
		mBinding.cvMineRecommend.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, RecommendCodeActivity.class));
			}
		});
		//邀请他人注册Vip
		mBinding.cvMineVip.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, ApplyForVipActivity.class));
			}
		});
		setAvatar(null);
		mMineViewModel.loadSellerInfo().setCallback(new OnChangeCallback<SellerInfoRsp>() {
			@Override
			public void onChange(SellerInfoRsp result) {
				updateSellerInfo(result);
			}
		});

	}

	private void shareToWeiXin(String content) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(getActivity(), Constants.WEI_XIN_APP_ID);
		if (msgApi.isWXAppInstalled()) {
			final View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_store_code, null);
			final LinearLayout linearLayout = view.findViewById(R.id.ll_erweima);
			final ImageView imageView = view.findViewById(R.id.iv_erweima);
			//生成二维码
			final String mErWeiMaFilePath1 = FileUtils.getAppFilePath(getActivity()) + File.separator + "qr_4" + System.currentTimeMillis() + ".jpg";
			boolean success1 = QrCodeUtil.createQRImage(content, AppUtils.dp2px(183), AppUtils.dp2px(183), FileUtils.getBitmap(getActivity(), R.mipmap.ic_app_icon), mErWeiMaFilePath1);
			if (success1) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						imageView.setImageBitmap(BitmapFactory.decodeFile(mErWeiMaFilePath1));
						Bitmap bmp = FileUtils.layoutViewAndGetBitmap(linearLayout, AppUtils.dp2px(183), AppUtils.dp2px(183));
						//初始化 WXImageObject 和 WXMediaMessage 对象
						WXImageObject imgObj = new WXImageObject(bmp);
						WXMediaMessage msg = new WXMediaMessage();
						msg.mediaObject = imgObj;
						//设置缩略图
						msg.thumbData = WeiXinUtils.bmpToByteArray(bmp,true);//内容大小不超过 10MB
						bmp.recycle();
						//构造一个Req
						SendMessageToWX.Req req = new SendMessageToWX.Req();
						req.transaction = "";//对应该请求的事务ID，通常由Req发起，回复Resp时应填入对应事务ID
						req.message = msg;
						req.scene = SendMessageToWX.Req.WXSceneSession;//分享到对话:SendMessageToWX.Req.WXSceneSession	分享到朋友圈:SendMessageToWX.Req.WXSceneTimeline;	分享到收藏:SendMessageToWX.Req.WXSceneFavorite
						req.userOpenId = Constants.WEI_XIN_APP_ID;
						//调用api接口，发送数据到微信
						msgApi.sendReq(req);
					}
				});
			}
		} else {
			AppUtils.showMessage("请先安装微信");
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		mMineViewModel.loadMineData().setCallback(this::updateMineData);
	}

	@Override
	public void onStart() {
		super.onStart();
		mBinding.viewBanner.startAutoPlay();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBinding.viewBanner.stopAutoPlay();
	}

	private void updateMineData(MineDataRsp mineDataRsp) {
		if (mineDataRsp == null) {
			return;
		}
		if (mineDataRsp.user != null)
			asiaCode = mineDataRsp.user.ysh;
		setAvatar(mineDataRsp.user);
		mBinding.tvUserName.setText(mineDataRsp.user == null ? "--" : mineDataRsp.user.nickname);
		setYaShi(mineDataRsp);
		setMsg(mineDataRsp);
		List<Integer> ordres = mineDataRsp.ordres;
		if (ordres != null && ordres.size() >= 4) {
			mBinding.viewOrder.setPayMsgCount(ordres.get(0));
			mBinding.viewOrder.setDeliveryMsgCount(ordres.get(1));
			mBinding.viewOrder.setReceiveMsgCount(ordres.get(2));
			mBinding.viewOrder.setCommentMsgCount(ordres.get(3));
		}
		mBinding.viewOptions.setCouponCount(mineDataRsp.coupun);
		mBinding.viewOptions.setRestMoney(mineDataRsp.money);
		mBinding.viewOptions.setMineLikeCount(mineDataRsp.collect);
		setSeller(mineDataRsp.user != null && mineDataRsp.user.isSeller());
	}

	private void setAvatar(UserDetail user) {
		ImageUtils.loadCircleImage(mBinding.ivAvatar, user == null ? R.mipmap.ic_default_head :
				TextUtils.isEmpty(user.avatar) ? R.mipmap.ic_default_head : user.avatar);
	}

	private void setYaShi(MineDataRsp mineDataRsp) {
		UserDetail user = mineDataRsp.user;
		mBinding.tvPhone.setText(user != null && user.isSeller() ? String.format("我的分销合伙人编号：%s", user.ysh) : "");
	}

	private void setMsg(MineDataRsp mineDataRsp) {
		if (mineDataRsp == null) {
			return;
		}
		mBinding.tvMsgCount.setText(String.valueOf(mineDataRsp.msg));
		mBinding.tvMsgCount.setVisibility(mineDataRsp.msg > 0 ? View.VISIBLE : View.INVISIBLE);
		mBinding.layoutMsg.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, MessageActivity.class));
			}
		});

	}

	private void setSeller(boolean seller) {
		if (seller) {
			mBinding.layoutTop.setBackground(null);
			mBinding.tvMineMyStoreCode.setVisibility(View.VISIBLE);
			mBinding.cvMineRecommend.setVisibility(View.VISIBLE);
		} else {
			mBinding.layoutTop.setBackgroundResource(R.drawable.bg_common_gradient);
			mBinding.tvMineMyStoreCode.setVisibility(View.GONE);
			mBinding.cvMineRecommend.setVisibility(View.GONE);
		}
		mBinding.ivTop.setVisibility(seller ? View.VISIBLE : View.GONE);
		mBinding.cvMineBanner.setVisibility(seller ? View.VISIBLE : View.GONE);
		mBinding.viewTaskPanel.setVisibility(seller ? View.VISIBLE : View.GONE);
		mBinding.viewSpace.setVisibility(!seller ? View.VISIBLE : View.GONE);
		mBinding.viewSellerOptions.setIsSeller(seller);
		mBinding.viewSellerResult.setVisibility(seller ? View.VISIBLE : View.GONE);
		mBinding.viewOptions.isSeller(seller);
	}


	public void updateSellerInfo(SellerInfoRsp sellerInfoRsp) {
		if (sellerInfoRsp == null) {
			return;
		}
		setBanner(sellerInfoRsp);
		//邀请他人注册图片
		ImageUtils.loadRoundedCornersImage(mBinding.ivMineRecommend, sellerInfoRsp.img, 8);
		mBinding.viewOptions.setMineScoreCount(sellerInfoRsp.score);
		mBinding.viewTaskPanel.setTaskCount(sellerInfoRsp.task_count);
		mBinding.viewTaskPanel.updateTask(sellerInfoRsp.task);
		SellerInfoRsp.SaleInfo saleInfo = sellerInfoRsp.sale;
		if (saleInfo != null) {
			mBinding.viewSellerResult.setYesterdayScore(String.valueOf(saleInfo.yesterday));
			mBinding.viewSellerResult.setYearScore(String.valueOf(saleInfo.year));
		}
	}

	private void setBanner(SellerInfoRsp sellerInfoRsp) {
		List<Banner> banners = sellerInfoRsp.adj;
		List<String> bannerUrls = new ArrayList<>();
		if (banners != null) {
			for (Banner banner : banners) {
				if (banner != null) {
					bannerUrls.add(banner.image);
				}
			}
		}
		mBinding.viewBanner.setImageLoader(new GlideImageLoader());
		mBinding.viewBanner.setOnBannerListener(position -> {
			if (bannerUrls != null) {
				Banner banner = banners.get(position);
				switch (banner.type) {
					case 1://商品
						if (banner != null && banner.aid != 0) {
							GoodsDetailActivity.start(mContext, banner.aid);
						}
						break;
					case 2://新闻
						if (banner != null && banner.aid != 0) {
							MonthlyDetailActivity.start(mContext, banner.aid);
						}
						break;
				}
			}
		});
		mBinding.viewBanner.setImages(bannerUrls);
		mBinding.viewBanner.start();
	}



}
