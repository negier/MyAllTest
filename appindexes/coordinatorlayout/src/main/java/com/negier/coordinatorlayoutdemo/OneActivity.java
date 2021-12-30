package com.negier.coordinatorlayoutdemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * ==========================================================
 *
 * 功能描述：
 * OneActivity.class
 * 这份代码应该是很多人对CoordinateLayout的第一印象，这也是关于CoordinateLayout最直观的解释。
 * 这里实现的效果就是如上面第三幅动图message中那样，底部弹出一个SnackBar，FloatingActionButton自动上移；这就是所谓的协调，协调FloatingActionButton上移，不被顶部弹出的SnackBar所遮挡。
 * 这里如果没有使用CoordinateLayout作为根布局，而是使用LinearLayout或RelativeLayout等，如果FloatingActionButton距离底部太近，那么它将会被底部弹出的Snackbar所遮挡。
 *
 * @author： NEGIER
 * @date： 2017/11/17 15:52
 *
 * ==========================================================
 */
public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "哈哈", Snackbar.LENGTH_SHORT).setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 您点击了
                    }
                }).show();
            }
        });
    }
}
