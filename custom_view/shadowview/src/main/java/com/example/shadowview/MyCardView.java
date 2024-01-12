package com.example.shadowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyCardView extends FrameLayout {

    private Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private RectF rectF = new RectF();
    private float corner = 30;
    private int shadowSize = 9;


    public MyCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        shadowPaint.setColor(Color.WHITE);
        shadowPaint.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        shadowPaint.setShadowLayer(shadowSize, 0, 0, Color.parseColor("#3A297269"));

        setPadding(shadowSize,shadowSize,shadowSize,shadowSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.set(shadowSize, shadowSize, getWidth()-shadowSize, getHeight()-shadowSize);
        path.addRoundRect(rectF, corner, corner, Path.Direction.CW);
        canvas.drawPath(path,shadowPaint);
        canvas.translate(shadowSize,shadowSize);

    }
}
