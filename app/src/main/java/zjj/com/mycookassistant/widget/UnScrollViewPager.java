package zjj.com.mycookassistant.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

/**
 * Created by Xuanhao on 2016/3/8.
 * UnScrollViewPager
 */
public class UnScrollViewPager extends ViewPager {

    private boolean isScrollable = false;

    public UnScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollViewPager(Context context) {
        super(context);

    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isScrollable)
            return super.onTouchEvent(arg0);
        boolean b = super.onTouchEvent(arg0);
        Logger.d("onTouchEvent" + b);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isScrollable && super.onInterceptTouchEvent(arg0);
    }
}
