package com.xuebinduan.draw_signing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TouchDrawSigningView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dipToPx(4f));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public TouchDrawSigningView(Context context) {
        super(context);
    }

    public TouchDrawSigningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDrawSigningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                path.moveTo(event.getX(),event.getY());
                invalidate();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                path.lineTo(event.getX(),event.getY());
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:{
//                path.reset();
//                invalidate();
                break;
            }
        }
        return true;
    }

    private int dipToPx(Float dip){
        return (int) ((Resources.getSystem().getDisplayMetrics().density*dip)+0.5f);
    }
}
