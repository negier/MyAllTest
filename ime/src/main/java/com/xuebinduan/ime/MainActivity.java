package com.xuebinduan.ime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.TextView;

/**
 * todo 需要android11，api30及以上才能使用
 * https://medium.com/androiddevelopers/animating-your-keyboard-fb776a8fb66d
 */
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textIMEVisibility;
    private TextView textIMESize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        textIMEVisibility = findViewById(R.id.text_ime_visibility);
        textIMESize = findViewById(R.id.text_ime_size);

        textIMEVisibility.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                //隐藏
                WindowInsetsController windowInsetsController = v.getWindowInsetsController();
                windowInsetsController.hide(WindowInsets.Type.ime());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void onCheck(View view){
        WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(editText);
        boolean visible= insets.toWindowInsets().isVisible(WindowInsets.Type.ime());
        textIMEVisibility.setText("键盘可见："+(visible?"可见":"不可见"));
        int height = insets.toWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
        textIMESize.setText("键盘宽高："+height);
    }

}