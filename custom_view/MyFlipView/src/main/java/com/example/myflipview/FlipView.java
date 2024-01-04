package com.example.myflipview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FlipView extends FrameLayout {
    private View priorView;
    private TextView priorTextView;
    private View flipView;
    private TextView flipTextView;
    private View nextView;
    private TextView nextTextView;
    //targetNum不能一开始就和lastTargetNum相同，但又不能等于用户可能取的初值0，所以就随意再选了一个负数
    private int targetNum=-2;
    //这个只是动画的方向，正计时：从上往下翻最后显示Prior；倒计时：从下往上翻，最后显示Next。
    private boolean scrollForwardDirection;
    private final int animDuration = 6600;
    private RectF AllRect = new RectF();
    private RectF topRect = new RectF();
    private RectF bottomRect = new RectF();
    private Scroller mScroller;
    // 翻转画布
    private Camera camera = new Camera();
    private Matrix matrix = new Matrix();
    private int lastTargetNum = -1;
    private int corner = 100;
    private final Path pathRoundRectAll = new Path();
    private final Path pathRoundRectTop = new Path();
    private final Path pathRoundRectBottom = new Path();
    private final Paint shadowPaint = new Paint();
    private static final int MAX_SHADOW_ALPHA = 127;// out of 255
    private float densityDpi;
    // 是深色吗？这个影响阴影的绘制，dark就是背部阴影；not dark就是盒盖阴影。
    private boolean isDark = true;

    public FlipView(@NonNull Context context) {
        super(context);
        init();
    }

    public FlipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        topRect.top = 0;
        topRect.left = 0;
        topRect.right = getWidth();
        topRect.bottom = getHeight() / 2f;

        bottomRect.top = getHeight() / 2f;
        bottomRect.left = 0;
        bottomRect.right = getWidth();
        bottomRect.bottom = getHeight();

        pathRoundRectTop.reset();
        float[] floats1 = {corner, corner, corner, corner, 0, 0, 0, 0};
        pathRoundRectTop.addRoundRect(topRect, floats1, Path.Direction.CW);

        pathRoundRectBottom.reset();
        float[] floats2 = {0, 0, 0, 0, corner, corner, corner, corner};
        pathRoundRectBottom.addRoundRect(bottomRect, floats2, Path.Direction.CW);

        AllRect.set(0, 0, getWidth(), getHeight());
        pathRoundRectAll.reset();
        float[] floatsAll = {corner, corner, corner, corner, corner, corner, corner, corner};
        pathRoundRectAll.addRoundRect(AllRect, floatsAll, Path.Direction.CW);
    }

    private void init() {
        corner = (int) getResources().getDimension(R.dimen.calendar_clock_page_round);
        priorView = LayoutInflater.from(getContext()).inflate(R.layout.item_flip_view, this, false);
        priorTextView = priorView.findViewById(R.id.text_num);
        nextView = LayoutInflater.from(getContext()).inflate(R.layout.item_flip_view, this, false);
        nextTextView = nextView.findViewById(R.id.text_num);
        flipView = LayoutInflater.from(getContext()).inflate(R.layout.item_flip_view, this, false);
        flipTextView = flipView.findViewById(R.id.text_num);
        setScrollForwardDirection(true);
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        if (isDark) {
            shadowPaint.setColor(Color.WHITE);
        }else {
            shadowPaint.setColor(Color.BLACK);
        }
        shadowPaint.setStyle(Paint.Style.FILL);
        densityDpi = getResources().getDisplayMetrics().densityDpi;
    }

    public void setScrollForwardDirection(boolean b) {
        scrollForwardDirection = b;
        removeAllViews();
        if (scrollForwardDirection) {
            addView(nextView);
            addView(priorView);
        } else {
            addView(priorView);
            addView(nextView);
        }
        addView(flipView);
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void setTextSize(int dp) {
        priorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        nextTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        flipTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    public void setDarkStyle(boolean dark){
        this.isDark = dark;
    }

    private void setPriorTextContent(int num) {
        priorTextView.setText(String.valueOf(num));
    }

    private void setFlipTextContent(int num) {
        flipTextView.setText(String.valueOf(num));
    }

    private void setNextTextContent(int num) {
        nextTextView.setText(String.valueOf(num));
    }

    public void flipTo(int targetNum) {
        this.targetNum = targetNum;
        if (scrollForwardDirection) {
            setNextTextContent(lastTargetNum);
            setPriorTextContent(targetNum);
        } else {
            setPriorTextContent(lastTargetNum);
            setNextTextContent(targetNum);
        }
        if (lastTargetNum < 0) {
            lastTargetNum = targetNum;
            return;
        }

        // 开始动画
        if (scrollForwardDirection) {
            mScroller.startScroll(0, 0, 0, 180, animDuration);
        } else {
            mScroller.startScroll(0, 180, 0, -180, animDuration);
        }
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(pathRoundRectAll);
//        drawChild(canvas,priorView,0);
//        drawChild(canvas,nextView,0);
//        drawChild(canvas,flipView,0);

        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            int degree = mScroller.getCurrY();
//            Log.e("TAGG", "mScroller.getCurrY():" + mScroller.getCurrY());

            if (scrollForwardDirection) {
                canvas.save();
                canvas.clipPath(pathRoundRectTop);
                drawChild(canvas, priorView, 0);
                canvas.restore();

                canvas.save();
                canvas.clipPath(pathRoundRectBottom);
                drawChild(canvas, nextView, 0);
                if (!isDark) {
                    final int alpha1 = (int) ((degree / 90f) * MAX_SHADOW_ALPHA);
                    shadowPaint.setAlpha(alpha1);
                    canvas.drawRect(bottomRect, shadowPaint);
                }
                canvas.restore();

                canvas.save();
                camera.save();
//                Log.e("TAG","densityDpi:"+densityDpi);
                camera.setLocation(0f,0f,-densityDpi);
                if (degree < 90) {
                    setFlipTextContent(lastTargetNum);
                    camera.rotateX(-degree);
                } else {
                    setFlipTextContent(targetNum);
                    canvas.scale(1f, -1f, getWidth() / 2f, getHeight() / 2f);
                    camera.rotateX(degree);
                }
                camera.getMatrix(matrix);
                positionMatrix();
                canvas.concat(matrix);
                if (degree < 90) {
                    canvas.clipPath(pathRoundRectTop);
                } else {
                    canvas.clipPath(pathRoundRectBottom);
                }
                drawChild(canvas, flipView, 0);
                if (!isDark) {
                    if (degree < 90) {
                        final int alpha = (int) ((degree / 90f) * MAX_SHADOW_ALPHA);
                        shadowPaint.setAlpha(alpha);
                        canvas.drawRect(topRect, shadowPaint);
                    }
                }else{
                    if (degree > 90) {
                        final int alpha = (int) (((180-degree) / 90f) * MAX_SHADOW_ALPHA);
                        shadowPaint.setAlpha(alpha);
                        canvas.drawRect(bottomRect, shadowPaint);
                    }
                }
                camera.restore();
                canvas.restore();
            } else {
                canvas.save();
                canvas.clipPath(pathRoundRectTop);
                drawChild(canvas, priorView, 0);
                canvas.restore();

                canvas.save();
                canvas.clipPath(pathRoundRectBottom);
                drawChild(canvas, nextView, 0);
                if (!isDark) {
                    final int alpha1 = (int) ((degree / 90f) * MAX_SHADOW_ALPHA);
                    shadowPaint.setAlpha(alpha1);
                    canvas.drawRect(bottomRect, shadowPaint);
                }
                canvas.restore();

                canvas.save();
                camera.save();
                Log.e("TAG","densityDpi:"+densityDpi);
                Log.e("TAGXX","degree:"+degree);
                camera.setLocation(0f,0f,-densityDpi);
                if (degree < 90) {
                    setFlipTextContent(targetNum);
                    camera.rotateX(-degree);
                } else {
                    setFlipTextContent(lastTargetNum);
                    canvas.scale(1f, -1f, getWidth() / 2f, getHeight() / 2f);
                    camera.rotateX(degree);
                }
                camera.getMatrix(matrix);
                positionMatrix();
                canvas.concat(matrix);
                if (degree < 90) {
                    canvas.clipPath(pathRoundRectTop);
                } else {
                    canvas.clipPath(pathRoundRectBottom);
                }
                drawChild(canvas, flipView, 0);
                if (!isDark) {
                    if (degree < 90) {
                        final int alpha = (int) ((degree / 90f) * MAX_SHADOW_ALPHA);
                        shadowPaint.setAlpha(alpha);
                        canvas.drawRect(topRect, shadowPaint);
                    }
                }else{
                    if (degree > 90) {
                        final int alpha = (int) (((180-degree) / 90f) * MAX_SHADOW_ALPHA);
                        shadowPaint.setAlpha(alpha);
                        canvas.drawRect(bottomRect, shadowPaint);
                    }
                }
                camera.restore();
                canvas.restore();
            }

            invalidate();
        } else {
            // end
            lastTargetNum = targetNum;
            if (scrollForwardDirection) {
                drawChild(canvas, priorView, 0);
            }else{
                drawChild(canvas, nextView, 0);
            }
        }

//        Log.e("TAG", "dispatchDraw");
    }

    private void positionMatrix() {
        matrix.preScale(0.25f, 0.25f);
        matrix.postScale(4.0f, 4.0f);
        matrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        matrix.postTranslate(getWidth() / 2, getHeight() / 2);
    }

}
