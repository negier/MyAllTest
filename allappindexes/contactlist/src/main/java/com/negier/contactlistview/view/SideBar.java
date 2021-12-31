package com.negier.contactlistview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.negier.contactlistview.R;


/**
 * Created by Administrator on 2017/1/24 0024.
 */

public class SideBar extends View {
    private Paint mPaint = new Paint();
    private String[] mLetters=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private int mChoose=-1;
    private TextView mTextView;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int letterWidth = getWidth();
        int letterHeight = getHeight();
        int singleLetterHeight = letterHeight / mLetters.length;
        for (int i = 0; i < mLetters.length; i++) {
            mPaint.setColor(Color.parseColor("#000000"));
            mPaint.setTextSize(30);//改变字体大小
            mPaint.setAntiAlias(true);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            if (i==mChoose){
                mPaint.setColor(Color.parseColor("#ffffff"));
                mPaint.setFakeBoldText(true);//设置防粗体文字效果，目的是点击后看着更宽大些
            }
            float x=letterWidth/2-mPaint.measureText(mLetters[i])/2;
            float y=singleLetterHeight+singleLetterHeight*i;
            canvas.drawText(mLetters[i],x,y,mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int touchLetter = (int) (y / getHeight() * mLetters.length);//获取它点击的是哪个字母
        switch (action){
            case MotionEvent.ACTION_UP:
                restore();
                break;
            default:
                setBackgroundResource(R.drawable.shape_sidebar_background);
                if (touchLetter!=mChoose){
                    if (touchLetter>=0&&touchLetter<mLetters.length){//使用时有出过角标越界异常，所以加此防止。
                        if (mTextView!=null){
                            mTextView.setVisibility(View.VISIBLE);
                            mTextView.setText(mLetters[touchLetter]);
                        }
                        if (mTouchLetterListener!=null){
                            mTouchLetterListener.OnTouchLetter(mLetters[touchLetter]);
                        }
                        mChoose=touchLetter;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }
    public void setTextView(TextView mTextView){
        this.mTextView=mTextView;
    }

    /**
     * 还原至初始状态
     */
    public void restore(){
        setBackgroundColor(Color.parseColor("#00ffffff"));
        mChoose=-1;
        if (mTextView!=null){
            mTextView.setVisibility(View.INVISIBLE);
        }
        invalidate();
    }
    /**
     * 对外提供的接口
     */
    private OnTouchLetterListener mTouchLetterListener;
    public void setOnTouchLetterListener(OnTouchLetterListener mTouchLetterListener){
        this.mTouchLetterListener=mTouchLetterListener;
    }
    public interface OnTouchLetterListener{
        void OnTouchLetter(String letter);
    }
}
