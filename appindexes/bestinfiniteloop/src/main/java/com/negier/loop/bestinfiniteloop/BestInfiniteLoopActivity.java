package com.negier.loop.bestinfiniteloop;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class BestInfiniteLoopActivity extends AppCompatActivity {
    private ViewPager viewPager;
    /**
     * 普通圆点的容器
     */
    private LinearLayout llGrayDot;
    /**
     * 目标圆点
     */
    private View redDot;
    private TextView textDesc;

    private final int MOVE_INTERVAL=3000;
    private final int MOVE_WHAT=0;
    /**
     * 圆点的大小
     * 修改了此值，建议去布局文件将redDot的宽高也改下
     */
    private final int DOT_DP=5;
    /**
     * 圆点与圆点之间的距离
     */
    private final int DOT_DISTANCE=5;
    private List<ImageData> mImageDataList;
    /**
     * 是否用手拖动过ViewPager
     */
    private boolean dragging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_infinite_loop);
        mImageDataList = loadImageDataList();
        initView();
        initDots();
        initData();
    }

    private void initData() {
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mImageDataList);
        viewPager.setAdapter(imagePagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                textDesc.setText(mImageDataList.get(position).getDesc());
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 这是对onPageScrolled回调机制的微调，因为到末尾position的时候positionOffset=0，再次滑动的时候，position还是末尾位置的值，但positionOffset还会有值变动
                if (position==mImageDataList.size()-2){
                    positionOffset=0;
                }

                if (position>mImageDataList.size()-2){
                    position=1;
                }
                position = position-1;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redDot.getLayoutParams();
                params.leftMargin = (int)(width*positionOffset + position*width);
                redDot.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int position = viewPager.getCurrentItem();
                int endPosition=mImageDataList.size()-1;
                //问题：如果不在闲置状态下setCurrentItem的话，屏幕会闪动。
                //原因：这个页面动画未完成，立即切换至另一个页面，因为这个页面动画还在进行中而另一个页面已经instantiateItem出来，那么这个页面就会被迅速回收，导致了闪屏的现象)。
                //参考：https://www.jianshu.com/p/99b9e4b53dc0
                if (state==ViewPager.SCROLL_STATE_IDLE){
                    //关键
                    if (position==0){
                        viewPager.setCurrentItem(endPosition-1,false);
                    }
                    if (position==endPosition){
                        viewPager.setCurrentItem(1,false);
                    }

                    if (dragging){
                        mHandler.sendEmptyMessageDelayed(MOVE_WHAT,MOVE_INTERVAL);
                        dragging = false;
                    }
                }
                if (state==ViewPager.SCROLL_STATE_DRAGGING){
                    mHandler.removeMessages(MOVE_WHAT);
                    dragging = true;
                }
            }
        });
        //关键
        viewPager.setCurrentItem(1);
    }

    private int width;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            sendEmptyMessageDelayed(MOVE_WHAT,MOVE_INTERVAL);
        }
    };
    private void initDots() {
        llGrayDot.removeAllViews();
        RelativeLayout.LayoutParams redDotParams = (RelativeLayout.LayoutParams) redDot.getLayoutParams();
        redDotParams.leftMargin = 0;
        redDot.setLayoutParams(redDotParams);
        int dotPx = dp2px(this, DOT_DP);
        for (int i = 0 ; i < mImageDataList.size()-2;i++){
            View grayDot = new View(this);
            grayDot.setBackgroundResource(R.drawable.shape_gray_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotPx, dotPx);
            if (i != 0 ){
                params.leftMargin = dp2px(this, DOT_DISTANCE);
            }
            grayDot.setLayoutParams(params);
            llGrayDot.addView(grayDot);
        }
        llGrayDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llGrayDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                width = llGrayDot.getChildAt(1).getLeft() - llGrayDot.getChildAt(0).getLeft();
                mHandler.sendEmptyMessageDelayed(MOVE_WHAT,MOVE_INTERVAL);
            }
        });
    }

    private List<ImageData> loadImageDataList() {
        List<ImageData> imageDataList = new ArrayList<>();
        //关键
        imageDataList.add(new ImageData(R.mipmap.m05,"美女五"));
        imageDataList.add(new ImageData(R.mipmap.m01,"美女一"));
        imageDataList.add(new ImageData(R.mipmap.m02,"美女二"));
        imageDataList.add(new ImageData(R.mipmap.m03,"美女三"));
        imageDataList.add(new ImageData(R.mipmap.m04,"美女四"));
        imageDataList.add(new ImageData(R.mipmap.m05,"美女五"));
        //关键
        imageDataList.add(new ImageData(R.mipmap.m01,"美女一"));
        return imageDataList;
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        llGrayDot = (LinearLayout) findViewById(R.id.ll_gray_dot);
        textDesc = (TextView) findViewById(R.id.text_desc);
        redDot = findViewById(R.id.red_dot);
    }

    public int dp2px(Context context, float dp){
        float density=context.getResources().getDisplayMetrics().density;
        int px=(int) (dp*density+0.5f);
        return px;
    }
}
