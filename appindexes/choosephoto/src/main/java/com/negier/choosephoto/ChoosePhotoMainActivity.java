package com.negier.choosephoto;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

/**
 * ==========================================================
 *
 * 功能描述：
 *  MainActivity.class
 *  authorities必须唯一，否则无法安装
 *
 * @author： NEGIER
 *
 * @date： 2017/10/25 17:49
 *
 * ==========================================================
 */

public class ChoosePhotoMainActivity extends AppCompatActivity {
    private ImageView picture;
    private final int REQUEST_CODE_GET_PIC = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choose_photo);
        picture = (ImageView) findViewById(R.id.picture);
        requestPermissions();
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },0);
    }

    public void click1(View view) {
        startActivityForResult(new Intent(this,ChoosePhotoActivity.class),REQUEST_CODE_GET_PIC);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_GET_PIC){
            if (resultCode==RESULT_OK){
                String imagePath = data.getStringExtra("imagePath");
                displayImage(imagePath);
            }
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(picture);
        } else {
            Toast.makeText(this, "选择失败", Toast.LENGTH_LONG).show();
        }
    }

}
