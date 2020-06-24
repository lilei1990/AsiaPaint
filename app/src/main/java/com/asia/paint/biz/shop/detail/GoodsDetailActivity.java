package com.asia.paint.biz.shop.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.banner.BannerConfig;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.image.RichImageLoader;
import com.asia.paint.base.model.CollectViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Comment;
import com.asia.paint.base.network.bean.Gift;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.network.bean.PromotionGroupPintuan;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.bean.User;
import com.asia.paint.base.util.WeiXinUtils;
import com.asia.paint.biz.mine.seller.store.PinTuanDialog;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.biz.order.group.GroupDetailActivity;
import com.asia.paint.databinding.ActivityGoodsDetailBinding;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.asia.paint.utils.utils.DigitUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.asia.paint.utils.utils.SpanText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.smarttop.library.utils.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author by chenhong14 on 2019-11-06.
 */
public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding> {

	private static final String KEY_ORDER_TYPE = "KEY_ORDER_TYPE";
	private static final String KEY_GOODS_ID = "KEY_GOODS_ID";
	private static final String KEY_GROUPBUY_ID = "KEY_GROUPBUY_ID";
	private static final String KEY_SPIKE_ID = "KEY_SPIKE_ID";
	private static final String DEFAULT_DELIVERY_TIPS = "国内部分地区不配送 : 部分地区包含 : \\n配送至以下地区邮费为X元 : 以下地区包含 :";

	private GoodsDetailViewModel mDetailViewModel;
	private CollectViewModel mCollectViewModel;
	private int mGoodsId;
	private int mGroupById;
	private int mSpikeId;
	private int mType;
	private ShopGoodsDetailRsp mGoodsDetailRsp;
	private Specs mSelectedSpecs;
	private int mSelectedCount;

	/**
	 * 拼团列表
	 */
	private List<PromotionGroupPintuan> mPinTuanList = new ArrayList<>();
	/**
	 * 拼团列表适配器
	 */
	private PinTuanAdapter mPinTuanAdapter = null;
	/**
	 * 赠品列表
	 */
	private List<Gift> mGiftList = new ArrayList<>();
	/**
	 * 赠品列表适配器
	 */
	private GiftAdapter mGiftAdapter = null;
	/**
	 * 组合购列表
	 */
	private List<PromotionComposeRsp.ResultBean> mPromotionComposeList = new ArrayList<>();
	/**
	 * 组合购列表适配器
	 */
	private CombinationShoppingAdapter mCombinationShoppingAdapter = null;
	/**
	 * 拼团弹框
	 */
	private PinTuanDialog mPinTuanDialog = null;

	public static void start(Context context, int goods_id) {
		Intent intent = new Intent(context, GoodsDetailActivity.class);
		intent.putExtra(KEY_ORDER_TYPE, OrderService.BUY);
		intent.putExtra(KEY_GOODS_ID, goods_id);
		context.startActivity(intent);
	}

	public static void startGroup(Context context, int goods_id, int groupbuy_id) {
		Intent intent = new Intent(context, GoodsDetailActivity.class);
		intent.putExtra(KEY_ORDER_TYPE, OrderService.GROUP);
		intent.putExtra(KEY_GOODS_ID, goods_id);
		intent.putExtra(KEY_GROUPBUY_ID, groupbuy_id);
		context.startActivity(intent);
	}

	public static void startSpike(Context context, int goods_id, int spike_id) {
		Intent intent = new Intent(context, GoodsDetailActivity.class);
		intent.putExtra(KEY_ORDER_TYPE, OrderService.SPIKE);
		intent.putExtra(KEY_GOODS_ID, goods_id);
		intent.putExtra(KEY_SPIKE_ID, spike_id);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mType = intent.getIntExtra(KEY_ORDER_TYPE, OrderService.BUY);
		mGoodsId = intent.getIntExtra(KEY_GOODS_ID, 0);
		mGroupById = intent.getIntExtra(KEY_GROUPBUY_ID, 0);
		mSpikeId = intent.getIntExtra(KEY_SPIKE_ID, 0);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_goods_detail;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDetailViewModel = getViewModel(GoodsDetailViewModel.class);
		mCollectViewModel = getViewModel(CollectViewModel.class);
		mBinding.viewBanner.setImageLoader(new RichImageLoader());
		mBinding.viewBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);

		mBinding.rvPintuan.setLayoutManager(new LinearLayoutManager(this));
		mPinTuanAdapter = new PinTuanAdapter();
		mBinding.rvPintuan.setAdapter(mPinTuanAdapter);

		mBinding.rvComplimentary.setLayoutManager(new LinearLayoutManager(this));
		mGiftAdapter = new GiftAdapter();
		mBinding.rvComplimentary.setAdapter(mGiftAdapter);

		mBinding.rvPromotionCompose.setLayoutManager(new LinearLayoutManager(this));
		mCombinationShoppingAdapter = new CombinationShoppingAdapter();
		mBinding.rvPromotionCompose.setAdapter(mCombinationShoppingAdapter);

		mBinding.ivLike.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mGoodsDetailRsp == null || mGoodsDetailRsp.result == null) {
					return;
				}
				Goods goods = mGoodsDetailRsp.result;
				if (goods.isCollect()) {
					mCollectViewModel.delCollect(goods.goods_id).setCallback(result -> {
						goods.is_collect = 0;
						mBinding.ivLike.setSelected(goods.isCollect());
					});
				} else {
					mCollectViewModel.addCollect(goods.goods_id).setCallback(result -> {
						goods.is_collect = 1;
						mBinding.ivLike.setSelected(goods.isCollect());
					});
				}
			}
		});
		mBinding.ivShare.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
					@Override
					public void subscribe(ObservableEmitter<Object> emitter) {
						shareToMiniProgram();
					}
				}).compose(new NetworkObservableTransformer<>())
						.subscribe(new Consumer<Object>() {
							@Override
							public void accept(Object o) {

							}
						});
			}
		});
		mBinding.ivBack.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				finish();
			}
		});
		mBinding.viewProtect.setTitle("保障");
		mBinding.viewProtect.setDescription("七天无理由退换货，包邮，送货入户");
		mBinding.viewProtect.setRightVisible(false);
		mBinding.viewSpec.setTitle("选择");
		mBinding.viewSpec.setDescription("请选择 规格及数量");
		mBinding.viewSpec.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mDetailViewModel.showGoodsSpecDialog(GoodsDetailActivity.this, mSelectedSpecs, mSelectedCount, GoodsSpecDialog.Type.SPEC)
						.setCallback((specs, count) -> {
							setSelectedSpecs(specs);
							setSelectedCount(count);
							mBinding.viewSpec.setDescription(mSelectedSpecs.spec_1);
						});
			}
		});

		mBinding.viewDelivery.setTitle("说明");
		mBinding.viewDelivery.setDescription("运费及配送说明");
		mBinding.viewDelivery.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				DeliveryDialog dialog = new DeliveryDialog();
				String description = mGoodsDetailRsp == null || TextUtils.isEmpty(mGoodsDetailRsp.freight)
						? DEFAULT_DELIVERY_TIPS : mGoodsDetailRsp.freight;
				dialog.setDescription(description);
				dialog.show(GoodsDetailActivity.this);
			}
		});
		//拼团列表查看所有
		mBinding.tvCheckAll.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mPinTuanDialog = PinTuanDialog.createInstance(mPinTuanList);
				mPinTuanDialog.show(GoodsDetailActivity.this);
				mPinTuanDialog.setChangeCallback(result -> {
					GroupDetailActivity.start(GoodsDetailActivity.this, Integer.valueOf(result), false);
				});
			}
		});
		//拼团列表去拼单按钮点击事件
		mPinTuanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			if (view.getId() == R.id.tv_join) {
				GroupDetailActivity.start(GoodsDetailActivity.this, mPinTuanList.get(position).log_id, false);
			}
		});
		//组合商品去购买
		mCombinationShoppingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			if (view.getId() == R.id.tv_combination_shopping_buy) {
				OrderCheckoutActivity.start(GoodsDetailActivity.this, OrderService.PROMOTION, mPromotionComposeList.get(position).compose_id, 1);
			}
		});
		if (mType == OrderService.GROUP) {//来自拼团
			//隐藏加入购物车按钮
			mBinding.viewBottomCart.hideAddCar(true);
			//设置"立即购买"按钮文案为"发起拼团"
			mBinding.viewBottomCart.setBtnBuyContent("发起拼团");
			//拼团详情
			mDetailViewModel.loadPromotionGroupDetail(mGoodsId, mGroupById).setCallback(this::updateShopGoodsDetail);
			//拼团列表
			mDetailViewModel.loadPromotionGroupPintuan(mGroupById).setCallback(result -> {
				mPinTuanList.clear();
				List<PromotionGroupPintuan> dataList = result;
				if (dataList == null || dataList.size() <= 0) {
					mBinding.llPintuan.setVisibility(View.GONE);
				} else {
					mBinding.llPintuan.setVisibility(View.VISIBLE);
					mPinTuanList.addAll(dataList);
				}
				mPinTuanAdapter.updateData(mPinTuanList);
				//开启定时器
				mTimer2 = new Timer();
				mTimerTask2 = new MyTimerTask2();
				mTimerTask2.run();
				mTimer2.schedule(mTimerTask2, 0, 1000);
			});
		} else if (mType == OrderService.SPIKE) {//来自秒杀
			//隐藏加入购物车按钮
			mBinding.viewBottomCart.hideAddCar(true);
			//拼团详情
			mDetailViewModel.loadSpikeGoodsDetail(mGoodsId, mSpikeId).setCallback(this::updateShopGoodsDetail);
		} else {
			//隐藏加入购物车按钮
			mBinding.viewBottomCart.hideAddCar(false);
			//商品详情
			mDetailViewModel.loadShopGoodsDetail(mGoodsId).setCallback(this::updateShopGoodsDetail);
			//组合列表
			mDetailViewModel.loadPromotionCompose(mGoodsId).setCallback(this::updatePromotionCompose);
		}
		LogUtil.e("mType0", mType + "");
		//让底部控件知晓是哪个购物方式
		mBinding.viewBottomCart.setType(mType);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mBinding.viewBanner.startAutoPlay();
	}

	@Override
	protected void onStop() {
		mBinding.viewBanner.stopAutoPlay();
		super.onStop();
	}

	private void shareToMiniProgram() {
		if (mGoodsDetailRsp == null || mGoodsDetailRsp.result == null) {
			LogUtil.e("mGoodsDetailRsp == null || mGoodsDetailRsp.result == null", (mGoodsDetailRsp == null) + "|" + (mGoodsDetailRsp.result == null));
			return;
		}
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, Constants.WEI_XIN_APP_ID);
		if (msgApi.isWXAppInstalled()) {
			Goods goods = mGoodsDetailRsp.result;
			WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
			miniProgramObj.webpageUrl = Constants.URL; // 兼容低版本的网页链接
			miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
			miniProgramObj.userName = "gh_6191d87d00a7";     // 小程序原始id
			miniProgramObj.path = "pages/index/spDetail/index?id=" + mGoodsId;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
			WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
			msg.title = goods.goods_name;                    // 小程序消息title
			msg.description = goods.spec_name_1;               // 小程序消息desc
			// 小程序消息封面图片，小于128k
   /*         Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bitmap.recycle();*/
			if (goods.default_image != null && goods.default_image.size() > 0) {
				String url = goods.default_image.get(0);
				Glide.with(this).asBitmap().load(url).centerCrop()
						.override(150, 150)
						.into(new SimpleTarget<Bitmap>() {
							@Override
							public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
								msg.thumbData = WeiXinUtils.bmpToByteArray(resource, true);
								SendMessageToWX.Req req = new SendMessageToWX.Req();
								req.transaction = "";
								req.message = msg;
								req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
								msgApi.sendReq(req);
							}
						});
			}
		} else {
			AppUtils.showMessage("请先安装微信");
		}

	}

/*
    private byte[] getThumbData() {
        Observable<File> observable = Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {

            }
        });

        return null;
    }*/

	private void setSelectedSpecs(Specs specs) {
		mSelectedSpecs = specs;
		mBinding.viewBottomCart.setSpecs(specs);
	}

	private void setSelectedCount(int count) {
		mSelectedCount = count;
		mBinding.viewBottomCart.setCount(count);
	}

	private void updateShopGoodsDetail(ShopGoodsDetailRsp goodsDetailRsp) {
		if (goodsDetailRsp == null || goodsDetailRsp.result == null) {
			return;
		}
		mGoodsDetailRsp = goodsDetailRsp;
		//赋值商品详情对象到底部控件中
		mBinding.viewBottomCart.setShopGoodsDetailRsp(mGoodsDetailRsp);
		Goods goods = mGoodsDetailRsp.result;
		mBinding.ivLike.setSelected(goods.isCollect());
		mBinding.viewBanner.setImages(goodsDetailRsp.result.default_image);
		mBinding.viewBanner.start();
		setPrice(goods.price);
		setOriginPrice(goods.market_price);
		setScore(String.valueOf(goods.level_1));
		if (mType != OrderService.GROUP && mType != OrderService.SPIKE) {
			mBinding.llGroupEndtimeNumber.setVisibility(View.GONE);
		} else {
			mTimer = new Timer();
			mTimerTask = new MyTimerTask();
			mTimerTask.run();
			mTimer.schedule(mTimerTask, 0, 1000);
			if (mType == OrderService.GROUP) {
				//团购截止日期和几人成团
				if (goodsDetailRsp._groupbuy != null) {
					mBinding.llGroupEndtimeNumber.setVisibility(View.VISIBLE);
					mBinding.tvGroupNumber.setText(goodsDetailRsp._groupbuy.number + "人");
				} else {
					mBinding.llGroupEndtimeNumber.setVisibility(View.GONE);
				}
			} else if (mType == OrderService.SPIKE) {
				//秒杀截止日期和几人成团
				if (goodsDetailRsp._spike != null) {
					mBinding.llGroupEndtimeNumber.setVisibility(View.VISIBLE);
					mBinding.llGroupNumber.setVisibility(View.GONE);
				} else {
					mBinding.llGroupEndtimeNumber.setVisibility(View.GONE);
				}
			}
		}
		//返券
		if (!TextUtils.isEmpty(goods.coupon_tip)) {
			mBinding.tvReturnTicket.setText(goods.coupon_tip);
			mBinding.tvReturnTicket.setVisibility(View.VISIBLE);
		} else {
			mBinding.tvReturnTicket.setText("");
			mBinding.tvReturnTicket.setVisibility(View.GONE);
		}
		mBinding.tvGoodsName.setText(goods.goods_name);
		//赠品
		setGift(mGoodsDetailRsp._gift);
		mBinding.viewBottomCart.setGoods(mGoodsDetailRsp.result);
		mBinding.viewGoodsShow.updateCateId(goodsDetailRsp.result.cate_id);
		// mBinding.tvGoodsDetail.setText(Html.fromHtml(goods.content));
		String content = goods.content;
		if (!TextUtils.isEmpty(content)) {
			//图片适应手机屏幕
			content = content.replace("<img", "<img style=\"display: block;max-width:100%;\"");
		}
		mBinding.wvDetail.loadData(content, "text/html", "UTF-8");
		mBinding.wvDetail.setWebViewClient(new WebViewClient());
		WebSettings webSettings = mBinding.wvDetail.getSettings();
		//不支持屏幕缩放
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		//不显示webview缩放按钮
		webSettings.setDisplayZoomControls(false);
		setComment(goodsDetailRsp);
	}

	/**
	 * 组合购数据
	 */
	private void updatePromotionCompose(PromotionComposeRsp dataBean) {
		mPromotionComposeList.clear();
		if (dataBean == null || dataBean.result == null || dataBean.result.size() <= 0) {
			mBinding.rvPromotionCompose.setVisibility(View.GONE);
		} else {
			mBinding.rvPromotionCompose.setVisibility(View.VISIBLE);
			mPromotionComposeList.addAll(dataBean.result);
		}
		mCombinationShoppingAdapter.updateData(mPromotionComposeList);
	}

	private void setComment(ShopGoodsDetailRsp goodsDetailRsp) {
		mBinding.viewComment.setGoodIs(goodsDetailRsp.result.goods_id);
		mBinding.viewComment.setCommentCount(goodsDetailRsp._comment_count);
		mBinding.viewComment.setCommentScore(goodsDetailRsp.score);
		List<Comment> comments = goodsDetailRsp._comment;
		if (comments != null && !comments.isEmpty()) {
			Comment comment = comments.get(0);
			if (comment != null) {
				mBinding.viewComment.setUserVisibility(true);
				User user = comment._user;
				if (user != null) {
					mBinding.viewComment.setUserAvatar(user.avatar);
					mBinding.viewComment.setUserName(user.nickname);
				}
				mBinding.viewComment.setUserCommentScore(DigitUtils.parseFloat(comment.comment_score, 0));
				mBinding.viewComment.setCommentTime(comment.comment_time);
				mBinding.viewComment.setCommentContent(comment.comment);
				mBinding.viewComment.addImage(comment.comment_images);
			}
		}
	}

	private void setPrice(String price) {
		if (TextUtils.isEmpty(price)) {
			return;
		}
		mBinding.tvGoodsPrice.setText(PriceUtils.getPrice(price));
	}

	private void setOriginPrice(String originPrice) {
		if (TextUtils.isEmpty(originPrice)) {
			return;
		}
		String targetText = PriceUtils.getPrice(originPrice);
		String originText = "原价 " + targetText;
		SpanText spanText = new SpanText.Builder()
				.origin(originText)
				.target(targetText)
				.setSpan(new StrikethroughSpan())
				.build();
		mBinding.tvGoodsOriginPrice.setText(spanText.toSpan());
	}

	private void setScore(String score) {
		if (TextUtils.isEmpty(score)) {
			return;
		}
		String targetText = score;
		String originText = "分销积分 " + targetText;
		SpanText spanText = new SpanText.Builder()
				.origin(originText)
				.target(targetText)
				.setSpan(new ForegroundColorSpan(AppUtils.getColor(R.color.color_FFF692)))
				.build();
		mBinding.tvScore.setVisibility(View.VISIBLE);
		mBinding.tvScore.setText(spanText.toSpan());
	}

	/**
	 * 赠品
	 */
	private void setGift(List<Gift> giftList) {
		mGiftList.clear();
		if (giftList != null && giftList.size() > 0) {
			mGiftList.addAll(giftList);
			mBinding.llComplimentary.setVisibility(View.VISIBLE);
		} else {
			mBinding.llComplimentary.setVisibility(View.GONE);
		}
		mGiftAdapter.updateData(mGiftList);
		//增加跳转商品详情功能
		mGiftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				GoodsDetailActivity.start(GoodsDetailActivity.this, Integer.valueOf(mGiftList.get(position).goods_id));
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBinding.viewBottomCart.removeCartCountCallback();
		mHandler.removeMessages(1);
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
		}
		mHandler2.removeMessages(1);
		if (mTimerTask2 != null) {
			mTimerTask2.cancel();
			mTimerTask2 = null;
		}
		if (mTimer2 != null) {
			mTimer2.cancel();
			mTimer2.purge();
			mTimer2 = null;
		}
	}

	private MyTimerTask mTimerTask;
	private Timer mTimer;

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			String endtime = null;
			if (mType == OrderService.GROUP) {
				if (mGoodsDetailRsp != null && mGoodsDetailRsp._groupbuy != null)
					endtime = mGoodsDetailRsp._groupbuy.endtime;
			} else if (mType == OrderService.SPIKE) {
				if (mGoodsDetailRsp != null && mGoodsDetailRsp._spike != null)
					endtime = mGoodsDetailRsp._spike.endtime;
			}
			if (TextUtils.isEmpty(endtime))
				return;

			long diffent = DateUtils.getMillisecond(DateUtils.getCurrentDate(), endtime);
			if (diffent > 0) {//当前时间小于截止时间
				setTimeShow(diffent);
			} else {

			}
		}
	}

	private void setTimeShow(long useTime) {
		useTime = useTime / 1000;
		int hour = (int) (useTime / 3600);
		int min = (int) (useTime / 60 % 60);
		int second = (int) (useTime % 60);
		int day = (int) (useTime / 3600 / 24);
		String mDay, mHour, mMin, mSecond;//天，小时，分钟，秒
		second--;
		if (second < 0) {
			min--;
			second = 59;
			if (min < 0) {
				min = 59;
				hour--;
			}
		}
		if (hour < 10) {
			mHour = "0" + hour;
		} else {
			mHour = "" + hour;
		}
		if (min < 10) {
			mMin = "0" + min;
		} else {
			mMin = "" + min;
		}
		if (second < 10) {
			mSecond = "0" + second;
		} else {
			mSecond = "" + second;
		}
		Message message = new Message();
		message.what = 1;
		message.obj = mHour + "," + mMin + "," + mSecond;
		mHandler.sendMessage(message);
	}

	private Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					LogUtil.e("msg.obj", msg.obj.toString());
					String time = (String) msg.obj;
					String timeArray[] = time.split(",");
					mBinding.tvShopHour1.setText(timeArray[0]);
					mBinding.tvShopMinute1.setText(timeArray[1]);
					mBinding.tvShopSecond1.setText(timeArray[2]);
					break;
				case 2:
					mBinding.tvShopHour1.setText("00");
					mBinding.tvShopMinute1.setText("00");
					mBinding.tvShopSecond1.setText("00");
					break;
			}
		}
	};

	private MyTimerTask2 mTimerTask2;
	private Timer mTimer2;

	class MyTimerTask2 extends TimerTask {
		@Override
		public void run() {
			if (mPinTuanList.isEmpty()) {
				return;
			}
			for (int i = 0; i < mPinTuanList.size(); i++) {
				if (!TextUtils.isEmpty(mPinTuanList.get(i).endtime)) {
					long diffent = DateUtils.getMillisecond(DateUtils.getCurrentDate(), mPinTuanList.get(i).endtime);
					if (diffent > 0) {//当前时间小于截止时间
						setTimeShow(diffent, i);
					} else {
						mPinTuanList.get(i).showHour = "00";
						mPinTuanList.get(i).showMinute = "00";
						mPinTuanList.get(i).showSecond = "00";
					}
				}
			}
			mHandler2.sendEmptyMessage(1);
		}
	}

	private void setTimeShow(long useTime, int i) {
		useTime = useTime / 1000;
		int hour = (int) (useTime / 3600);
		int min = (int) (useTime / 60 % 60);
		int second = (int) (useTime % 60);
		int day = (int) (useTime / 3600 / 24);
		String mDay, mHour, mMin, mSecond;//天，小时，分钟，秒
		second--;
		if (second < 0) {
			min--;
			second = 59;
			if (min < 0) {
				min = 59;
				hour--;
			}
		}
		if (hour < 10) {
			mHour = "0" + hour;
		} else {
			mHour = "" + hour;
		}
		if (min < 10) {
			mMin = "0" + min;
		} else {
			mMin = "" + min;
		}
		if (second < 10) {
			mSecond = "0" + second;
		} else {
			mSecond = "" + second;
		}
		mPinTuanList.get(i).showHour = mHour;
		mPinTuanList.get(i).showMinute = mMin;
		mPinTuanList.get(i).showSecond = mSecond;
	}

	private Handler mHandler2 = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					mPinTuanAdapter.notifyDataSetChanged();
					//刷新拼团弹框中列表
					if (mPinTuanDialog != null)
						mPinTuanDialog.updateList(mPinTuanList);
					break;
			}
		}
	};
}
