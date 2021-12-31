package com.negier.coverneteasemusicoverridependingtransition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ==========================================================
 *
 * 功能描述：
 *  MainActivity.class
 *  请原谅我取这么蹩脚的名字，还这么长，请求原谅，~~~
 *
 * @author： NEGIER
 *
 * @date： 2017/11/4 12:49
 *
 * ==========================================================
 */
public class NeteaseMusicAnimMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_netease_music);
    }

    /**
     * 网易云音乐开屏动画
     * @param view
     */
    public void playSplashAnim(View view){
        startActivity(new Intent(this,SplashAnimOneActivity.class));
    }

    /**
     * 网易云音乐通用Activity动画
     * @param view
     */
    public void playActivityAnim(View view){
        startActivity(new Intent(this,CustomAnimActivity.class));
    }
}
