package pl.jp.quizletapp.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import lombok.val;

public class CustomViewPager extends ViewPager {

    private float initialXValue;
    private SwipeDirection direction;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.ALL;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSwipeAllowed(event)) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    private boolean isSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.ALL) return true;

        if (direction == SwipeDirection.NONE)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            val diffX = event.getX() - initialXValue;
            // swipe from right to left detected
            if (diffX > 0 && direction.equals(SwipeDirection.RIGHT)) {
                // swipe from left to right detected
                return false;
            } else if(diffX < 0 && direction.equals(SwipeDirection.LEFT))
                return false;
        }

        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }
}
