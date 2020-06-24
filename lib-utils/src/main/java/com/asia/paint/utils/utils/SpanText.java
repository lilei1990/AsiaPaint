package com.asia.paint.utils.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;

/**
 * @author by chenhong14 on 2019-11-03.
 */
public final class SpanText {

    private Builder mBuilder;
    private SpannableString mSpan;

    private SpanText(Builder builder) {
        mBuilder = builder;
        if (TextUtils.isEmpty(mBuilder.origin) || TextUtils.isEmpty(mBuilder.target)) {
            return;
        }
        int start = mBuilder.origin.indexOf(mBuilder.target);
        if (start > -1) {
            int end = start + mBuilder.target.length();
            mSpan = new SpannableString(mBuilder.origin);
            mSpan.setSpan(mBuilder.span, start, end, mBuilder.flags);
        }
    }

    public SpannableString toSpan() {
        return mSpan;
    }

    public static class Builder {

        private String origin;
        private String target;
        private int flags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        private Object span;

        public String getOrigin() {
            return origin;
        }

        public Builder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public String getTarget() {
            return target;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public Builder setSpan(Object span) {
            this.span = span;
            return this;
        }


        public SpanText build() {
            return new SpanText(this);
        }

        public int getFlags() {
            return flags;
        }

        public Builder flags(int flags) {
            this.flags = flags;
            return this;
        }
    }
}
