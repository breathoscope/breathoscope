package org.inspire.breath.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class StaticPager extends ViewPager {

    private boolean sliding = false;

    public StaticPager(@NonNull Context context) {
        super(context);
    }

    public StaticPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent evt) {
        return this.sliding ? super.onInterceptTouchEvent(evt) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        return this.sliding ? super.onTouchEvent(evt) : false;
    }
}
