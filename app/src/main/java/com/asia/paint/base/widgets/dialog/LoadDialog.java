package com.asia.paint.base.widgets.dialog;

import android.widget.LinearLayout;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.databinding.DialogLoadBinding;
import com.asia.paint.utils.utils.AppUtils;

/**
 * Created by Administrator on 2020/6/27.
 */

public class LoadDialog extends BaseDialogFragment<DialogLoadBinding> {

    @Override
    protected void initView() {

    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(200);
    }

    @Override
    protected int getDialogHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_load;
    }
}

