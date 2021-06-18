package com.xuebinduan.windowmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class WindowManagerUtil {
    private static WindowManager mWindowManager;
    public static void popupChildWindow(Activity activity, View view, int anchorX, int anchorY){
        IBinder token = activity.getWindow().getDecorView().getWindowToken();
        if (token != null){
            mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0, 0,
                    PixelFormat.TRANSPARENT);
            if (view.getLayoutParams()!=null){
                mLayoutParams.width = view.getLayoutParams().width;
                mLayoutParams.height = view.getLayoutParams().height;
            }
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
            mLayoutParams.gravity = Gravity.START | Gravity.TOP;
            mLayoutParams.x = anchorX;
            mLayoutParams.y = anchorY;
            mLayoutParams.token = token;
            mWindowManager.addView(view, mLayoutParams);
        }else{
            throw new IllegalStateException("请在父窗口绘制流程结束时调用，Activity#onWindowFocusChanged、View#post(runnable)、ViewTreeObserver");
        }
    }
    public static void removeWindow(View view){
        if (mWindowManager!=null){
            mWindowManager.removeView(view);
        }
    }
}
