package com.asia.paint.biz.order.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.R;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.network.bean.PinTuanDetail;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.base.util.WeiXinUtils;
import com.asia.paint.biz.mine.seller.goals.WebViewActivity;
import com.asia.paint.biz.order.mine.detail.OrderDetailActivity;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.biz.shop.index.ShopViewModel;
import com.asia.paint.databinding.ActivityGroupDetailBinding;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.smarttop.library.utils.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 拼团详情
 *
 * @author tangkun
 */
public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding> {

	private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
	private static final String KEY_IS_OWNER = "KEY_IS_OWNER";
	private int mGroupId;
	private boolean mIsOwner = true;
	private GroupDetailMemberAdapter mGroupDetailMemberAdapter;
	private GroupDetailViewModel mGroupDetailViewModel;
	private ShopViewModel mGoodsViewModel;
	private PinTuanDetail result;

	public static void start(Context context, int groupId) {
		Intent intent = new Intent(context, GroupDetailActivity.class);
		intent.putExtra(KEY_GROUP_ID, groupId);
		context.startActivity(intent);
	}

	public static void start(Context context, int groupId, boolean isOwner) {
		Intent intent = new Intent(context, GroupDetailActivity.class);
		intent.putExtra(KEY_GROUP_ID, groupId);
		intent.putExtra(KEY_IS_OWNER, isOwner);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mGroupId = intent.getIntExtra(KEY_GROUP_ID, mGroupId);
		mIsOwner = intent.getBooleanExtra(KEY_IS_OWNER, true);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_group_detail;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "拼团详情";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGoodsViewModel = getViewModel(ShopViewModel.class);
		mGroupDetailViewModel = getViewModel(GroupDetailViewModel.class);
		mGroupDetailViewModel.resetPage();
		mBinding.rvGroupDetailMember.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
		mGroupDetailMemberAdapter = new GroupDetailMemberAdapter();
		mBinding.rvGroupDetailMember.setAdapter(mGroupDetailMemberAdapter);
		mBinding.tvGroupDetailRulesTitle.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				WebViewActivity.start(GroupDetailActivity.this, OtherService.PINTUAN_RULE);
			}
		});
		mBinding.btnGroupDetail.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				comfrimClick();
			}
		});
		initView();
		loadPintuanDetail();
	}

	/**
	 * 底部按钮点击事件
	 */
	private void comfrimClick() {
		if (result != null) {
			if (DateUtils.compareDate(DateUtils.getCurrentDate(), result.endtime)) {
				//当前日期大于结束日期
				if (result.need == 0) {
					//查看详情
					OrderDetailActivity.start(GroupDetailActivity.this, result.order_id);
				} else {
					//再开一团
					share();
				}
			} else {
				//当前日期小于或者等于结束日期
				//当前日期大于结束日期
				if (result.need == 0) {
					//查看详情
					OrderDetailActivity.start(GroupDetailActivity.this, result.order_id);
				} else {
					if (mIsOwner) {
						///邀请好友参团
						share();
					} else {
						//我要参团
						int groupbuy_id = result.groupbuy_id;
						mGoodsViewModel.loadGroupDetail(null, groupbuy_id).setCallback(result -> {
							ShopGoodsDetailRsp mShopGoodsDetailRsp = result;
							mGoodsViewModel.showGoodsSpecDialog(GroupDetailActivity.this, OrderService.GROUP, groupbuy_id, mShopGoodsDetailRsp._specs, mShopGoodsDetailRsp.goods_number, mShopGoodsDetailRsp.result, GoodsSpecDialog.Type.BUY);
						});
					}

				}
			}
		}
	}

	private void initView() {
		mBinding.tvGroupDetailRulesTitle.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
		mBinding.tvGroupDetailRulesTitle.getPaint().setAntiAlias(true);//抗锯齿
		mBinding.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间加横线
		mBinding.tvPrice.getPaint().setAntiAlias(true);//抗锯齿
	}


	private void loadPintuanDetail() {
		mGroupDetailViewModel.loadPintuanDetail(mGroupId).setCallback(result -> {
			this.result = result;
			mGroupDetailViewModel.updateListData(mGroupDetailMemberAdapter, result._pintuan);
			if (result.default_image != null && result.default_image.size() > 0) {
				ImageUtils.loadRoundedCornersImage(mBinding.ivIcon, result.default_image.get(0), 4);
			}
			if (!TextUtils.isEmpty(result.goods_name)) {
				mBinding.tvName.setText(result.goods_name);
			} else {
				mBinding.tvName.setText("");
			}
			mBinding.tvPriceGroup.setText(PriceUtils.getPrice(result.price));
			mBinding.tvPrice.setText(PriceUtils.getPrice(result.market_price));
			if (DateUtils.compareDate(DateUtils.getCurrentDate(), result.endtime)) {
				//当前日期大于结束日期
				if (result.need == 0) {
					mBinding.btnGroupDetail.setText("查看详情");
					mBinding.ivGroupDetailStatus.setImageResource(R.mipmap.ic_checkbox_selected);
					mBinding.ivGroupDetailStatus.setVisibility(View.VISIBLE);
					mBinding.tvGroupDetailStatus.setText("恭喜您拼团成功！已有" + (result.number - 1) + "人跟团购买");
					mBinding.llPintuanTimer.setVisibility(View.GONE);
				} else {
					mBinding.btnGroupDetail.setText("再开一团");
					mBinding.ivGroupDetailStatus.setImageResource(R.mipmap.ic_member_need);
					mBinding.ivGroupDetailStatus.setVisibility(View.VISIBLE);
					mBinding.tvGroupDetailStatus.setText("很遗憾！跟团人数未达标，拼团失败");
					mBinding.llPintuanTimer.setVisibility(View.GONE);
				}
			} else {
				//当前日期小于或者等于结束日期
				//当前日期大于结束日期
				if (result.need == 0) {
					mBinding.btnGroupDetail.setText("查看详情");
					mBinding.ivGroupDetailStatus.setImageResource(R.mipmap.ic_checkbox_selected);
					mBinding.ivGroupDetailStatus.setVisibility(View.VISIBLE);
					mBinding.tvGroupDetailStatus.setText("恭喜您拼团成功！已有" + (result.number - 1) + "人跟团购买");
					mBinding.llPintuanTimer.setVisibility(View.GONE);
				} else {
					mTimer = new Timer();
					mTimerTask = new MyTimerTask();
					mTimerTask.run();
					mTimer.schedule(mTimerTask, 0, 1000);
					if (mIsOwner) {
						mBinding.btnGroupDetail.setText("邀请好友参团");
						mBinding.ivGroupDetailStatus.setImageResource(R.mipmap.ic_member_need);
						mBinding.ivGroupDetailStatus.setVisibility(View.GONE);
//						mBinding.tvGroupDetailStatus.setText("还差" + result.need + "人拼团成功，还剩");
						mBinding.llPintuanTimer.setVisibility(View.VISIBLE);
					} else {
						mBinding.btnGroupDetail.setText("我要参团");
						mBinding.ivGroupDetailStatus.setImageResource(R.mipmap.ic_member_need);
						mBinding.ivGroupDetailStatus.setVisibility(View.GONE);
//						mBinding.tvGroupDetailStatus.setText("还差" + result.need + "人拼团成功，还剩");
						mBinding.llPintuanTimer.setVisibility(View.VISIBLE);
					}
				}
			}
			mBinding.tvGroupDetailRules.setText(result.number + "人拼团-人满发货-不满退款");
		});
	}

	private void share() {
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

	/**
	 * 邀请好友参团
	 */
	private void shareToMiniProgram() {
		if (result == null) {
			LogUtil.e("tangkun", "分享到微信小程序result为null");
			return;
		}
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, Constants.WEI_XIN_APP_ID);
		if (msgApi.isWXAppInstalled()) {
			WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
			miniProgramObj.webpageUrl = Constants.URL; // 兼容低版本的网页链接
			miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
			miniProgramObj.userName = "gh_6191d87d00a7";     // 小程序原始id
			miniProgramObj.path = "pages/car/joinGroup/index?groupbuy_id=" + result.groupbuy_id + "&log_id=" + result.log_id;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
			WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
			msg.title = "【仅剩下" + result.need + "个名额】我拼了【" + result.goods_name + "】";                    // 小程序消息title
			msg.description = result.goods_name;               // 小程序消息desc
			// 小程序消息封面图片，小于128k
   /*         Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bitmap.recycle();*/
			if (result.default_image != null && result.default_image.size() > 0) {
				msg.thumbData = WeiXinUtils.getHtmlByteArray(result.default_image.get(0));
			}
			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = "";
			req.message = msg;
			req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
			msgApi.sendReq(req);
		} else {
			AppUtils.showMessage("请先安装微信");
		}
	}

	private MyTimerTask mTimerTask;
	private Timer mTimer;

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			String endtime = null;
			if (result != null)
				endtime = result.endtime;
			if (TextUtils.isEmpty(endtime)) {
				return;
			}
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
					mBinding.tvGroupDetailStatus.setText("还差" + result.need + "人拼团成功，还剩");
					mBinding.tvShopHour1.setText(timeArray[0]);
					mBinding.tvShopMinute1.setText(timeArray[1]);
					mBinding.tvShopSecond1.setText(timeArray[2]);
					break;
			}
		}
	};

	@Override
	public void onDestroy() {
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
		super.onDestroy();
	}
}
