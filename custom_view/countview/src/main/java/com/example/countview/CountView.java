package com.example.mycountview.countview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mycountview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbinqiang on 15/10/2017.
 */

public class CountView extends View {

    private static final int FONT_SIZE = 12;
    private String text = "";
    private StaticLayout mStaticLayout;
    private String prefix;
    private long count;
    private String suffix;
    private float locationX;
    private float locationY = 0;
    //每个数字字符的宽度
    private int unitX;
    private long mCurCount;
    private long mNewCount;
    private List<String> mCurDigitalList = new ArrayList<>(); // 当前数量各位数字的列表
    private List<String> mNewDigitalList = new ArrayList<>(); // 即将变化数量各位数字列表
    private float DEFAULT_TEXT_SIZE;
    private ValueAnimator mObjectAnimator;
    private float mCurAniValue;    //当前属性动画数值
    private TextPaint mTextPaint;
    private String countStr;
    private Paint paint;

    public CountView(Context context) {
        this(context, null);
    }

    public CountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimator();
    }

    public void setText(String prefix, long count, String suffix) {
        this.prefix = prefix;
        this.count = count;
        this.suffix = suffix;
        mCurCount = count;
        countStr = String.valueOf(count);
        unitX = (int) mTextPaint.measureText("0");
        StringBuilder placeholder = new StringBuilder();
        float prefixWidth = mTextPaint.measureText(prefix);
        float targetWidth = mTextPaint.measureText(countStr);
        float currentWidth = 0;
        while (currentWidth < targetWidth) {
            placeholder.append(" ");
            currentWidth = mTextPaint.measureText(placeholder.toString());
        }
        locationX = prefixWidth + (currentWidth - targetWidth) / 2f;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //baseline是零线
        locationY = -fontMetrics.ascent;
        this.text = prefix + placeholder + suffix;

        toDigitals(count, mCurDigitalList);
        toDigitals(count, mNewDigitalList);

        requestLayout();
    }

    private void initPaint() {
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(FONT_SIZE * getResources().getDisplayMetrics().density);
        mTextPaint.setColor(Color.BLACK);
        DEFAULT_TEXT_SIZE = mTextPaint.getTextSize();

        paint = new Paint();
        paint.setAntiAlias(mTextPaint.isAntiAlias());
        paint.setTextSize(mTextPaint.getTextSize());
        paint.setColor(mTextPaint.getColor());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthAskedFor = MeasureSpec.getSize(widthMeasureSpec);
        int width = ((int) mTextPaint.measureText(text));
        widthAskedFor = Math.min(width, widthAskedFor);

        mStaticLayout = new StaticLayout(text, mTextPaint, widthAskedFor, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height;
        if (heightMode == MeasureSpec.AT_MOST) {
            height = mStaticLayout.getHeight() + getPaddingTop() + getPaddingBottom();
        } else {
            throw new RuntimeException("View height must be 'wrap_content'.");
        }
        setMeasuredDimension(widthAskedFor, height);
    }

    public void changeCount(long change) {
        //先结束当前动画
        endAnimator();
        this.mNewCount = mCurCount + change;
        if (mNewCount < 0) {
            setText(prefix,0,suffix);
            return;
        }
        toDigitals(mCurCount, mCurDigitalList);
        toDigitals(mNewCount, mNewDigitalList);
        if (mNewCount != mCurCount) {
            startAnimator();
        }
    }

    /**
     * 数字转为字符串列表
     *
     * @param num
     * @param digitalList
     */
    private void toDigitals(long num, List<String> digitalList) {
        digitalList.clear();
        if (num == 0) {
            String mZeroText = "0";
            digitalList.add(mZeroText);
        }
        while (num > 0) {
            digitalList.add(0, String.valueOf(num % 10));
            num = num / 10;
        }
    }

    public void initAnimator() {
        mObjectAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mObjectAnimator.setDuration(500);
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurAniValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //动画结束， 数值更新
                mCurCount = mNewCount;
                count = mNewCount;
                //比如原来是100，现在是99，会有一个空格的空隙，需要刷新一下
                setText(prefix, count, suffix);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void startAnimator() {
        if (mObjectAnimator != null) {
            mObjectAnimator.start();
        }
    }

    public void endAnimator() {
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.end();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mStaticLayout.draw(canvas);
        drawImpl(canvas, locationX, locationY, unitX, mCurAniValue, paint);
    }

    public void drawImpl(Canvas canvas, float initX, float initY, int unitX, float curAniValue, Paint paint) {
        float ANIM_HEIGHT = locationY;
        int len = mNewDigitalList.size();
        for (int i = 0; i < len; i++) {
            String newDigitalChar = mNewDigitalList.get(i);
            String oldDigitalChar = "";
            if (mCurDigitalList.size() > i) {
                oldDigitalChar = mCurDigitalList.get(i);
            }
            float x = unitX * i + initX;
            if (newDigitalChar.equals(oldDigitalChar)) {
                //只绘制新的数字
                canvas.drawText(String.valueOf(newDigitalChar), x, initY, paint);
            } else if (mNewCount > mCurCount) {
                //旧数字消失动画
                if (!TextUtils.isEmpty(oldDigitalChar)) {
                    drawOut(canvas, oldDigitalChar, x, initY - (curAniValue * ANIM_HEIGHT), paint);
                }
                //新数字进入动画绘制
                drawIn(canvas, newDigitalChar, x, initY + (ANIM_HEIGHT - curAniValue * ANIM_HEIGHT), paint);
            } else {
                if (!TextUtils.isEmpty(oldDigitalChar)) {
                    drawOut(canvas, oldDigitalChar, x, initY + (curAniValue * ANIM_HEIGHT), paint);
                }
                drawIn(canvas, newDigitalChar, x, initY - (ANIM_HEIGHT - curAniValue * ANIM_HEIGHT), paint);
            }
        }
    }

    /**
     * @param canvas
     * @param digital
     * @param x
     * @param y
     */
    public void drawIn(Canvas canvas, String digital, float x, float y, Paint inPaint) {
        inPaint.setAlpha((int) (mCurAniValue * 255));
        inPaint.setTextSize(DEFAULT_TEXT_SIZE * (mCurAniValue * 0.5f + 0.5f));
        canvas.drawText(digital, x, y, inPaint);
        inPaint.setAlpha(255);
        inPaint.setTextSize(DEFAULT_TEXT_SIZE);
    }

    /**
     * @param canvas
     * @param digital
     * @param x
     * @param y
     */
    public void drawOut(Canvas canvas, String digital, float x, float y, Paint outPaint) {
        outPaint.setAlpha(255 - (int) (mCurAniValue * 255));
        outPaint.setTextSize(DEFAULT_TEXT_SIZE * (1.0f - mCurAniValue * 0.5f));
        canvas.drawText(digital, x, y, outPaint);
        outPaint.setAlpha(255);
        outPaint.setTextSize(DEFAULT_TEXT_SIZE);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mObjectAnimator.end();
    }
}
