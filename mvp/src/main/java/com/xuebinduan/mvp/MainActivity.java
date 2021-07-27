package com.xuebinduan.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Presenter.IView {
    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.edit_1);
        editText2 = findViewById(R.id.edit_2);

        new Presenter(this).init();
    }

    @Override
    public void showData(List<String> data) {
        editText1.setText(data.get(0));
        editText2.setText(data.get(1));
    }
}