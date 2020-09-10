package com.asia.paint.biz.cart;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentCartBinding;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.CartGoods;
import com.asia.paint.base.network.bean.CartGoodsRsp;
import com.asia.paint.base.util.CartUtils;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.PriceUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment<FragmentCartBinding> {

	public enum Mode {
		EMPTY, CART, EDIT
	}

	private Mode mCurMode = Mode.EMPTY;

	private ShopCartAdapter mShopCartAdapter;
	private CartViewModel mViewModel;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_cart;
	}

	@Override
	protected void initView() {
		mViewModel = getViewModel(CartViewModel.class);
		mBinding.rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
		mShopCartAdapter = new ShopCartAdapter();
		mShopCartAdapter.setCallBack(new ShopCartAdapter.ShopCartCallback() {
			@Override
			public void onUpdate() {
				loadCartGoods();
			}

			@Override
			public void onCheck(boolean isChecked, CartGoods cartGoods) {
				mViewModel.checkToCart(cartGoods.rec_id, isChecked ? 1 : 0).setCallback(result -> loadCartGoods());

			}

			@Override
			public void onEditCheckUpdate() {
				mBinding.cbAllCheck.setChecked(mShopCartAdapter.isAllCheck());
			}
		});
		mBinding.rvGoods.addItemDecoration(new RecyclerView.ItemDecoration() {
			int space = AppUtils.dp2px(8);

			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				outRect.set(0, 0, 0, space);
			}
		});
		mBinding.rvGoods.setAdapter(mShopCartAdapter);

		mBinding.tvCheckout.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				startActivity(new Intent(mContext, OrderCheckoutActivity.class));
			}
		});

		mBinding.btnTry.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				MainActivity.start(getContext(), MainActivity.Tab.SHOP.getValue());
			}
		});

		mBinding.cbAllCheck.setListener(new CheckBox.OnCheckChangeListener() {
			@Override
			public void onChange(boolean isChecked) {
				if (mCurMode == Mode.CART) {
					mViewModel.checkAllToCart(!isChecked ? 1 : 0).setCallback(result -> loadCartGoods());
				} else if (mCurMode == Mode.EDIT) {
					mShopCartAdapter.setAllCheck(isChecked);
				}
			}

			@Override
			public boolean changeBySelf() {
				return mCurMode == Mode.EDIT;
			}
		});
		mBinding.tvModify.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mCurMode == Mode.CART) {
					setMode(Mode.EDIT);
				} else if (mCurMode == Mode.EDIT) {
					setMode(Mode.CART);
				}
			}
		});
		mBinding.btnRemove.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				List<CartGoods> selectedCardGoods = mShopCartAdapter.getSelectedCardGoods();
				if (selectedCardGoods.isEmpty()) {
					return;
				}
				if (mShopCartAdapter.isAllCheck()) {
					mViewModel.deleteAllCart().setCallback(result -> loadCartGoods());
				} else {
					mViewModel.deleteCart(CartUtils.getRecIds(selectedCardGoods)).setCallback(result -> loadCartGoods());
				}
			}
		});
		mBinding.tvCheckout.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (mShopCartAdapter.getCheckCount() <= 0) {
					ToastUtils.showShortToast(getActivity(), "请选择商品");
					return;
				}
				OrderCheckoutActivity.start(mContext, OrderService.CART);
			}
		});
		setMode(Mode.EMPTY);

	}


	@Override
	public void onResume() {
		super.onResume();
		loadCartGoods();
	}

	private void loadCartGoods() {
		mViewModel.loadCartGoods().setCallback(this::updateCartGoods);

	}


	private void updateCartGoods(CartGoodsRsp cartGoodsRsp) {
		boolean isEmpty = cartGoodsRsp == null || cartGoodsRsp.cartGoods == null || cartGoodsRsp.cartGoods.isEmpty();
		setMode(isEmpty ? Mode.EMPTY : mCurMode == Mode.EMPTY ? Mode.CART : mCurMode);
		mShopCartAdapter.replaceData(cartGoodsRsp == null ? new ArrayList<>() : cartGoodsRsp.cartGoods);
		mBinding.cbAllCheck.setChecked(mShopCartAdapter.isAllCheck());
		mBinding.tvTotalPrice.setText(PriceUtils.getPrice(cartGoodsRsp == null ? "" : cartGoodsRsp.amount));
		mBinding.tvCheckout.setText(String.format("结算(%s)", cartGoodsRsp == null ? 0 : mShopCartAdapter.getCheckCount()));
		mBinding.tvGoodsCount.setText(String.format("共%s件商品", cartGoodsRsp == null ? 0 : cartGoodsRsp.quantity));
		AsiaPaintApplication.queryCartCount();
	}

	private void setMode(Mode mode) {
		mCurMode = mode;
		switch (mCurMode) {
			case EMPTY:
				setEmptyMode();
				break;
			case CART:
				setCartMode();
				break;
			case EDIT:
				setEditMode();
				break;
		}
	}

	private void setEmptyMode() {
		mBinding.tvModify.setVisibility(View.GONE);
		mBinding.rvGoods.setVisibility(View.GONE);
		mBinding.layoutTotal.setVisibility(View.GONE);
		mBinding.layoutEmpty.setVisibility(View.VISIBLE);
	}

	private void setCartMode() {
		mBinding.tvModify.setVisibility(View.VISIBLE);
		mBinding.rvGoods.setVisibility(View.VISIBLE);
		mBinding.layoutTotal.setVisibility(View.VISIBLE);
		mBinding.layoutEmpty.setVisibility(View.GONE);
		mBinding.layoutCheckout.setVisibility(View.VISIBLE);
		mBinding.btnRemove.setVisibility(View.GONE);
		mBinding.tvModify.setText("管理");
		mShopCartAdapter.setMode(Mode.CART);
		mBinding.cbAllCheck.setChecked(mShopCartAdapter.isAllCheck());
	}


	private void setEditMode() {
		mBinding.tvModify.setVisibility(View.VISIBLE);
		mBinding.rvGoods.setVisibility(View.VISIBLE);
		mBinding.layoutTotal.setVisibility(View.VISIBLE);
		mBinding.layoutEmpty.setVisibility(View.GONE);
		mBinding.layoutCheckout.setVisibility(View.GONE);
		mBinding.btnRemove.setVisibility(View.VISIBLE);
		mBinding.tvModify.setText("完成");
		mShopCartAdapter.setMode(Mode.EDIT);
		mBinding.cbAllCheck.setChecked(mShopCartAdapter.isAllCheck());
	}

}
