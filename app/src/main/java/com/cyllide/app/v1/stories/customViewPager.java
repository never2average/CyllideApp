package com.cyllide.app.v1.stories;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import java.lang.reflect.Field;
import androidx.viewpager.widget.ViewPager;


public class customViewPager extends ViewPager {

    public customViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    public customViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // stop swipe
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // stop switching pages
        return false;
    }

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}
