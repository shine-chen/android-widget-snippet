import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shinechen on 10/13/15.
 */
public class LockableNestedScrollView extends NestedScrollView {
    private static final String TAG = "LockableNestedSV";

    public interface ScrollViewListener {
        void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    /// Whether the scroll view is scrollable.
    private boolean mScrollable = true;
    /// Whether the scroll view have higher priority to consume scrolling before its decedents.
    private boolean mScrollBeforeDecedents = true;
    private ScrollViewListener mScrollViewListener = null;

    public LockableNestedScrollView(Context context) {
        super(context);
    }

    public LockableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockableNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Pass touch event to super if scrollable
                if (mScrollable) {
                    return super.onTouchEvent(ev);
                }

                return false;
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Do nothing with intercepted touch events if not scrollable
        if (!mScrollable) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    public boolean isScrollBeforeDecedents() {
        return mScrollBeforeDecedents;
    }

    public void setScrollBeforeDecedents(boolean scrollBeforeDecedents) {
        this.mScrollBeforeDecedents = scrollBeforeDecedents;
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollViewListener(ScrollViewListener listener) {
        mScrollViewListener = listener;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        if (!mScrollBeforeDecedents) {
            // Do nothing, default behavior.
            return;
        }

        // Scroll the parent scroll view prior to the inner one
        int totalHeight = getChildAt(0).getHeight();
        if (getScrollY() + getHeight() < totalHeight) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
    }
}
