package com.asia.paint.biz.shop.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.acker.simplezxing.activity.CaptureActivity;
import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentShopBinding;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.image.GlideImageLoader;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Banner;
import com.asia.paint.base.network.bean.FlashGoods;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.bean.UpdateStatusBean;
import com.asia.paint.base.util.WeiXinUtils;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.login.LoginActivity;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.biz.mine.seller.score.cash.CashActivity;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.biz.pay.password.SetPayPwdActivity;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.biz.shop.flash.FlashGoodsFragment;
import com.asia.paint.biz.shop.goods.GoodsFragment;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class ShopFragment extends BaseFragment<FragmentShopBinding> {

    private static final String CATEGORY_FLASH_SALE = "秒杀";
    private static final String CATEGORY_GROUP = "火爆拼团";
    private static final String CATEGORY_ALL = "全部";

    private ShopViewModel mViewModel;
    private GoodsPagerAdapter mGoodsPagerAdapter;
    private List<Banner> mBanners;
    /**
     * 组合购对象
     */
    private PromotionComposeRsp mPromotionComposeRsp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        mViewModel = getViewModel(ShopViewModel.class);
        //分享
        mBinding.ivShopShare.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                shareToMiniProgram();
            }
        });
        mBinding.viewBanner.setImageLoader(new GlideImageLoader());
        mBinding.viewBanner.setOnBannerListener(position -> {
            if (mBanners != null) {
                Banner banner = mBanners.get(position);
                if (banner != null && banner.goods_id != null && banner.goods_id != 0) {
                    GoodsDetailActivity.start(mContext, banner.goods_id);
                }
            }
        });
        mGoodsPagerAdapter = new GoodsPagerAdapter(getChildFragmentManager());
        mBinding.viewPager.setAdapter(mGoodsPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.ivScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XXPermissions.with(getActivity())
                        .permission(Manifest.permission.CAMERA)
                        .request(new OnPermission() {
                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), CaptureActivity.REQ_CODE);
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                AppUtils.showMessage("没有开启相机权限，无法使用扫码功能");
                            }
                        });
            }
        });
        //监听
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mBinding.tabLayout.getSelectedTabPosition() >= 0 && mBinding.tabLayout.getSelectedTabPosition() < mBinding.tabLayout.getTabCount() / 3) {
                    mBinding.viewPoint1.setBackgroundColor(getResources().getColor(R.color.color_2F54CC));
                    mBinding.viewPoint2.setBackgroundColor(getResources().getColor(R.color.color_999999));
                    mBinding.viewPoint3.setBackgroundColor(getResources().getColor(R.color.color_999999));
                } else if (mBinding.tabLayout.getSelectedTabPosition() >= mBinding.tabLayout.getTabCount() / 3 && mBinding.tabLayout.getSelectedTabPosition() < mBinding.tabLayout.getTabCount() / 3 * 2) {
                    mBinding.viewPoint1.setBackgroundColor(getResources().getColor(R.color.color_999999));
                    mBinding.viewPoint2.setBackgroundColor(getResources().getColor(R.color.color_2F54CC));
                    mBinding.viewPoint3.setBackgroundColor(getResources().getColor(R.color.color_999999));
                } else {
                    mBinding.viewPoint1.setBackgroundColor(getResources().getColor(R.color.color_999999));
                    mBinding.viewPoint2.setBackgroundColor(getResources().getColor(R.color.color_999999));
                    mBinding.viewPoint3.setBackgroundColor(getResources().getColor(R.color.color_2F54CC));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //秒杀专区
        mBinding.llShopFlashSale.setOnClickListener(v -> {
            ShopBannerRsp.CategoryBean bean = new ShopBannerRsp.CategoryBean();
            bean.name = "秒杀";
            GroupAndFlashActivity.start(getActivity(), bean);
        });
        //拼团专区
        mBinding.llShopGroup.setOnClickListener(v -> {
            ShopBannerRsp.CategoryBean bean = new ShopBannerRsp.CategoryBean();
            bean.name = "火爆拼团";
            GroupAndFlashActivity.start(getActivity(), bean);
        });
        //组合购买
        mBinding.llShopCombination.setOnClickListener(v -> {
            if (mPromotionComposeRsp != null && mPromotionComposeRsp.result != null && mPromotionComposeRsp.result.size() > 0) {
                OrderCheckoutActivity.start(getActivity(), OrderService.PROMOTION, mPromotionComposeRsp.result.get(0).compose_id, 1);
            }
        });
        mViewModel.loadBanner().setCallback(shopBannerRsp -> {
            updateBanners(shopBannerRsp);
            updateCategory(shopBannerRsp);
        });
        //获取组合列表数据
        mViewModel.loadPromotionCompose().setCallback(result -> {
            mPromotionComposeRsp = result;
        });
        //app图片
        mViewModel.loadIndexBase().setCallback(result -> {
            Glide.with(this).load(result.spike).placeholder(R.mipmap.ic_default).into(mBinding.ivMiaosha);
            Glide.with(this).load(result.pintuan).placeholder(R.mipmap.ic_default).into(mBinding.ivPintuan);
            Glide.with(this).load(result.conpose).placeholder(R.mipmap.ic_shop_group).into(mBinding.ivZuhegou);
            CacheUtils.put(CacheUtils.KEY_SMZ, result.smz);
        });
    }

    /**
     * 分享
     */
    private void shareToMiniProgram() {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(getActivity(), Constants.WEI_XIN_APP_ID);
        if (msgApi.isWXAppInstalled()) {
            WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
            miniProgramObj.webpageUrl = Constants.URL; // 兼容低版本的网页链接
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = "gh_6191d87d00a7";     // 小程序原始id
            miniProgramObj.path = "";            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
            WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
            msg.title = "亚士漆";                    // 小程序消息title
            msg.description = "亚士商城";               // 小程序消息desc
            // 小程序消息封面图片，小于128k
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_main_share_bg);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bitmap.recycle();
            msg.thumbData = WeiXinUtils.bmpToByteArray(sendBitmap, true);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "";
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
            msgApi.sendReq(req);
        } else {
            AppUtils.showMessage("请先安装微信");
        }
    }

    private void updateBanners(ShopBannerRsp shopBannerRsp) {
        List<String> banners = new ArrayList<>();
        mBanners = new ArrayList<>();
        if (shopBannerRsp == null || shopBannerRsp.adj == null) {
            return;
        }
        for (Banner adj : shopBannerRsp.adj) {
            if (adj != null) {
                banners.add(adj.image);
                mBanners.add(adj);
            }
        }
        mBinding.viewBanner.setImages(banners);
        mBinding.viewBanner.start();
    }

    private void updateCategory(ShopBannerRsp shopBannerRsp) {
        if (shopBannerRsp == null || shopBannerRsp.category == null) {
            return;
        }
        addLocalCategory(shopBannerRsp.category);
        mGoodsPagerAdapter.update(shopBannerRsp.category);
    }

    private void addLocalCategory(List<ShopBannerRsp.CategoryBean> categoryBeanList) {
        if (categoryBeanList == null) {
            return;
        }
        ShopBannerRsp.CategoryBean categoryGroup = new ShopBannerRsp.CategoryBean();
        categoryGroup.name = CATEGORY_GROUP;
        categoryBeanList.add(0, categoryGroup);
        ShopBannerRsp.CategoryBean categoryFlashSale = new ShopBannerRsp.CategoryBean();
        categoryFlashSale.name = CATEGORY_FLASH_SALE;
        categoryBeanList.add(0, categoryFlashSale);
        ShopBannerRsp.CategoryBean categoryAll = new ShopBannerRsp.CategoryBean();
        categoryAll.name = CATEGORY_ALL;
        categoryBeanList.add(0, categoryAll);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.viewBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        mBinding.viewBanner.stopAutoPlay();
        super.onStop();
    }

    public class GoodsPagerAdapter extends FragmentStatePagerAdapter {


        private List<ShopBannerRsp.CategoryBean> mCategory = new ArrayList<>();

        public GoodsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ShopBannerRsp.CategoryBean categoryBean = mCategory.get(position);
            if (TextUtils.equals(categoryBean.name, CATEGORY_FLASH_SALE) || TextUtils.equals(categoryBean.name, CATEGORY_GROUP)) {
                return FlashGoodsFragment.create(categoryBean);
            }
            return GoodsFragment.create(categoryBean);
        }

        @Override
        public int getCount() {
            return mCategory.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            ShopBannerRsp.CategoryBean category = mCategory.get(position);
            return category != null ? category.name : "";
        }

        public void update(List<ShopBannerRsp.CategoryBean> categorys) {
            mCategory.clear();
            if (categorys != null) {
                mCategory.addAll(categorys);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CaptureActivity.REQ_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            String url = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
            if (TextUtils.isEmpty(url)) {
                ToastUtils.showLongToast(getActivity(), "二维码为空，请重新扫描");
                return;
            }
            requestCode(url);
        }
    }


    /**
     * 通过扫描二维码获取的信息调用扫码接口，获取对应信息，执行对应操作
     */
    private void requestCode(String url) {
        mViewModel.updateStatus(url).setCallback(result -> {
            UpdateStatusBean bean = result;
            if (bean != null && !TextUtils.isEmpty(bean.msg)) {
                new MessageDialog.Builder()
                        .title(bean.msg)
                        .setSureButton("确认", null)
                        .build()
                        .show(getActivity());
            }
        });
    }
}
