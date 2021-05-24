package com.xuebinduan.windowmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 有三种窗口类型被定义在WindowManager类中：
 * 1 应用窗口(层值<99)，Activity的默认窗口为TYPE_APPLICATION
 * 2 子窗口(层值：1000~1999)
 * 3 系统窗口(层值：2000~2999)，有些系统窗口只能被添加一次否则用户会觉得很乱，比如输入法窗口，所以WmS接收到创建窗口的消息时会进行一定的检查。（需要申请权限）
 * WmS在进行窗口叠加时，会动态调整层值。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Settings.canDrawOverlays(this)  检查是否有悬浮窗权限
        if(!Settings.canDrawOverlays(this)){
            //没有悬浮窗权限,跳转申请
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);
        }else {

            WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            // WindowManager.LayoutParams.WRAP_CONTENT
            WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                    800, 260, 0, 0,
                    PixelFormat.TRANSPARENT);
            //FLAG_NOT_TOUCH_MODAL在此模式下，系统会将当前Window区域外的单击事件传递给底层的Window,当前Window区域内的单击事件则自己处理
            //FLAG_NOT_FOCUSABLE表示Window不需要焦点，不需要接收输入
            //FLAG_SHOW_WHEN_LOCKED此模式下Window可以显示在锁屏的界面上
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            //type参数表示Window的类型
            mLayoutParams.type = Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            //set the animation for the window
            mLayoutParams.windowAnimations = android.R.style.Animation_Translucent;
            mLayoutParams.gravity = Gravity.CENTER | Gravity.TOP;
            mLayoutParams.x = 0;
            mLayoutParams.y = 100;
            mLayoutParams.token = new Binder();
            TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.shape_round_black_bg);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWindowManager.removeView(textView);
                }
            });
            mWindowManager.addView(textView, mLayoutParams);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(getStringDate());
                            }
                        });
                        SystemClock.sleep(1000);
                    }
                }
            }).start();

        }


        //唤醒屏幕
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(30_000);
                awakeScreenLight();
                //todo 弹出一个前台通知
            }
        }).start();

    }

    private void awakeScreenLight(){
        KeyguardManager km= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();

        PowerManager pm=(PowerManager) getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //获取锁
        wl.acquire();
        //释放锁
        wl.release();
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd \n HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


}