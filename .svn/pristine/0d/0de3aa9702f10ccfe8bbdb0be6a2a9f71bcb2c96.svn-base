package com.asia.paint.base.container;

import androidx.databinding.ViewDataBinding;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public abstract class BaseTitleActivity<T extends ViewDataBinding> extends BaseActivity<T> {


    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return middleTitle();
    }

    protected abstract String middleTitle();
}
