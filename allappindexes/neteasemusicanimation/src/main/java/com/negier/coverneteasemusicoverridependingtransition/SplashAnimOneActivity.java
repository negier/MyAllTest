package com.negier.coverneteasemusicoverridependingtransition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * ==========================================================
 *
 * 功能描述：
 *  SplashAnimOneActivity.class
 *  分析下动画：
 *      1.第一个Activity,缩小【1~0.5】的同时，透明度【1~0】变低
 *      2.第二个Activity,本身先放大在缩小【1.5~1】
 *
 * @author： NEGIER
 *
 * @date： 2017/11/4 14:12
 *
 * ==========================================================
 */
public class SplashAnimOneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_anim_one);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashAnimOneActivity.this,SplashAnimTwoActivity.class));
                overridePendingTransition(R.anim.anim_splash_enter,R.anim.anim_splash_quit);
                finish();
            }
        },2000);
    }

}
