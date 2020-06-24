package com.asia.paint.base.util;

import android.text.InputFilter;
import android.text.Spanned;

public class MoneyFilter implements InputFilter {

    public MoneyFilter() {
        super();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
            int dend) {
        String money = dest.toString() + source;
        if (MoneyUtils.isLikeMoney(money)) {
            return source;
        }
        return "";
    }

}