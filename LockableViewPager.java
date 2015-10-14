import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shinechen on 9/14/15.
 */
public class LockableViewPager extends ViewPager {

    private boolean mSwipeable = true;

    public LockableViewPager(Context context) {
        super(context);
    }

    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }

    public boolean getSwipeable() {
        return mSwipeable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mSwipeable) {
                    return super.onTouchEvent(event);
                }
                return false;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mSwipeable) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }


}
