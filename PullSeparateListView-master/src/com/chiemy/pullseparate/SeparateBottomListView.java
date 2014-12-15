
package com.chiemy.pullseparate;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * @author simon
 */
public class SeparateBottomListView extends ListView {
    private String TAG = SeparateBottomListView.class.getSimpleName();
    /**
     * 分离后恢复的动画时长
     */
    private static final long SEPARATE_RECOVER_DURATION = 1000;
    /**
     * 摩擦系数
     */
    public static final float FACTOR = 0.25f;
    /**
     * 按下的位置(在屏幕中的位置)
     */
    public int downPosition = -1;

    public float deltaY;

    // private GestureDetector mGestureDetector;

    public boolean mIsUpScroll = false;
    public boolean mIsScrolling = false;
    private Boolean[] AnimationState = null;

    public SeparateBottomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeparateBottomListView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SeparateBottomListView(Context context) {
        super(context);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        this.setDivider(null);
        this.setSelector(new BitmapDrawable());

        ViewConfiguration.get(getContext()).getScaledTouchSlop();
        super.setOnScrollListener(listener);

    }

    /**
     * 恢复
     */
    private void recoverSeparate() {
        int childCount = getChildCount();
        AnimationState = new Boolean[childCount];
        for (int i = 0; i < AnimationState.length; i++) {
            AnimationState[i] = new Boolean(false);
        }
        Log.d(TAG, "recoverSeparate AnimationState.length:" + AnimationState.length
                + " getChildCount()=" + getChildCount());

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            final int out_index = i;
            ViewPropertyAnimator.animate(child).translationY(0)
                    .setDuration(SEPARATE_RECOVER_DURATION)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListener() {
                        Integer index = new Integer(out_index);

                        @Override
                        public void onAnimationStart(Animator arg0) {
                            // TODO Auto-generated method stub
                            Log.d(TAG, "onAnimationStart index:" + index);
                        }

                        @Override
                        public void onAnimationRepeat(Animator arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator arg0) {
                            // TODO Auto-generated method stub
                            Log.d(TAG,
                                    "onAnimationEnd AnimationState.length:"
                                            + AnimationState.length
                                            + " index=" + index);
                            AnimationState[index] = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

        }
    }

    private boolean isAnimRunning() {
        boolean isRunning = false;
        if (AnimationState == null) {
            isRunning = false;
        }
        else {
            for (int i = 0; i < AnimationState.length; i++) {
                if (!AnimationState[i]) {
                    isRunning = true;
                    break;
                }
            }
        }
        Log.d(TAG, "isSeperateRecovered getChildCount()=" + getChildCount() + " isRunning="
                + isRunning);
        return isRunning;
    }

    private int mLastVisiblePos = 0;
    private boolean isUpScroll = false;
    private OnScrollListener listener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                if (!isAnimRunning()) {
                    recoverSeparate();
                }

            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                int visibleItemCount, int totalItemCount) {
            if (mLastVisiblePos < firstVisibleItem) {
                isUpScroll = true;

            } else if (mLastVisiblePos > firstVisibleItem) {
                isUpScroll = false;
            }
            mLastVisiblePos = firstVisibleItem;

            Log.d(TAG, "isUpScroll=" + isUpScroll);
            if (isUpScroll && !isAnimRunning()) {
                seperateItem();
            }
        }
    };

    /**
     * 是否到达顶部
     * 
     * @return
     */
    @Deprecated
    protected boolean isReachTopBound() {
        int firstVisPos = getFirstVisiblePosition();
        if (firstVisPos == 0) {
            View firstView = getChildAt(firstVisPos);
            if (firstView != null && firstView.getTop() >= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否到达底部
     * 
     * @return
     */
    @Deprecated
    protected boolean isReachBottomBound() {
        int lastVisPos = getLastVisiblePosition();
        if (lastVisPos == getCount() - 1) {
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView != null && lastView.getBottom() <= getHeight()
                    && getCount() > getChildCount()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void seperateItem() {

        int visibleCount = getChildCount();
        deltaY = 50;
        int lastTranY = (int) getChildAt(visibleCount - 2).getTranslationY();

        Log.d(TAG, "translationY lastTranY=" + lastTranY);
        getChildAt(visibleCount - 1).setTranslationY(lastTranY + deltaY);
    }
}
