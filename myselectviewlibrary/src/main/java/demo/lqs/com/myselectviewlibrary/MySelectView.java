package demo.lqs.com.myselectviewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lin on 2017/4/6.
 */

public class MySelectView extends View implements ViewPager.OnPageChangeListener {
    private static final String SEPARATOR = "@";
    private ViewPager mViewPage;
    private int mPosition;
    private int mTitleTextSize = 30;
    private int mSubTitleTextSize = 20;
    private int mTitleColor = 0xff000000;
    private int mSubTitleColor = 0xff333333;
    private int mSelectTitleColor = 0xffffffff;
    private int mSelectCricleColor = 0xff00ff00;
    private int height;
    private int width;
    private Paint mTitleTextPaint;
    private Paint mSubTitleTextPaint;
    private Paint mSelectCriclePaint;
    private float x;
    private float mScrollX;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC_OVER;
    PorterDuffXfermode mXfermode = new PorterDuffXfermode(mPorterDuffMode);


    public MySelectView(Context context) {
        super(context);
    }

    public MySelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setClickable(true);

        mTitleTextPaint = new Paint();
        mTitleTextPaint.setColor(mTitleColor);
        mTitleTextPaint.setTextSize(mTitleTextSize);
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);

        mSubTitleTextPaint = new Paint();
        mSubTitleTextPaint.setColor(mSubTitleColor);
        mSubTitleTextPaint.setTextSize(mSubTitleTextSize);
        mSubTitleTextPaint.setTextAlign(Paint.Align.CENTER);

        mSelectCriclePaint = new Paint();
        mSelectCriclePaint.setColor(mSelectCricleColor);
        mSelectCriclePaint.setXfermode(mXfermode);

        mSelectCriclePaint.setTextSize(20);

    }

    public void setViewPage(ViewPager viewPage) {
        this.mViewPage = viewPage;
        mViewPage.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setPosition(int position) {
        this.mPosition = position;
        this.mViewPage.setCurrentItem(position);
        invalidate();
    }

    private String getTitle(int position) {
        String pageTitle = (String) mViewPage.getAdapter().getPageTitle(position);
        String[] split = pageTitle.split(SEPARATOR);
        return split[0];
    }

    private String getSubtitle(int position) {
        String pageTitle = (String) mViewPage.getAdapter().getPageTitle(position);
        String[] split = pageTitle.split(SEPARATOR);
        return split[1];
    }

    public static String joinTitle(String title, String subtitle) {
        return title + SEPARATOR + subtitle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        height = canvas.getHeight();
        width = canvas.getWidth();

        canvas.translate(width / 2, height / 2);
//        int save = canvas.save();
        mSelectCriclePaint.setXfermode(null);
        mSelectCriclePaint.setColor(mSelectTitleColor);
        canvas.drawCircle(0, 0, height / 2, mSelectCriclePaint);
        mSelectCriclePaint.setColor(mSelectCricleColor);
//        int save = canvas.saveLayer(); todo
        canvas.drawCircle(0, 0, height / 2, mSelectCriclePaint);
        mSelectCriclePaint.setXfermode(mXfermode);
        int position = mViewPage.getCurrentItem();
        canvas.translate(-position * height, 0);
        canvas.translate(mScrollX,0);
        for (int i = 0; i < mViewPage.getAdapter().getCount(); i++) {
            mSelectCriclePaint.setColor(0xff000000);
            canvas.drawText(getTitle(i), 0, -height / 5, mSelectCriclePaint);
            canvas.drawText(getSubtitle(i), 0, height / 4, mSelectCriclePaint);
            canvas.translate(height, 0);
        }
        mSelectCriclePaint.setXfermode(null);
//        canvas.restoreToCount(save);
//        canvas.drawCircle(0, 0, height / 2, mSelectCriclePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                float x1 = event.getX();
                scroll(x1 - x);
                x = x1;
            }
        }
        return super.onTouchEvent(event);
    }

    public void scroll(float x){
        this.mScrollX += x;

        if(this.mScrollX < -height/2){
            int childCount = mPosition + 1;
            mScrollX = height/2;
            mViewPage.setCurrentItem(childCount);
        }
        if(this.mScrollX > height/2){
            int childCount = mPosition -1;
            mScrollX =  -height/2;
            mViewPage.setCurrentItem(childCount);
        }

        Log.e("scroll",x + "/" +mScrollX + "/"+ mViewPage.getCurrentItem());
        invalidate();
    }


}
