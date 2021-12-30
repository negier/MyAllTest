package com.negier.coverneteasemusicoverridependingtransition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * ==========================================================
 *
 * 功能描述：
 *  CustomAnimActivity.class
 *  分析下动画：
 *      只有一个退出动画,动画就是在短时间内向下位移【0~20%p】并改变透明度【0.8~0】
 *
 * @author： NEGIER
 *
 * @date： 2017/11/4 16:46
 *
 * ==========================================================
 */
public class CustomAnimActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_anim);

        Toast.makeText(this,"请点击返回键查看动画",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,R.anim.anim_custom_quit);
    }
}
