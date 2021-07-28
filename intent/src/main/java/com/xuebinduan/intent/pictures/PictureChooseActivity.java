package com.xuebinduan.intent.pictures;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xuebinduan.intent.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 长按多选
 */
public class PictureChooseActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> picturesPicker;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_choose);
        imageView = this.<ImageView>findViewById(R.id.image_view);

        picturesPicker = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), new ActivityResultCallback<List<Uri>>() {
            @Override
            public void onActivityResult(List<Uri> result) {
                imageView.setImageURI(result.get(0));
                obtainFileInfo(result.get(0));
                result.forEach(uri -> {
                    Log.e("TAG", "图片地址：" + uri);
                });
            }
        });


    }

    private void obtainFileInfo(Uri uri) {
        try {
            getContentResolver().openInputStream(uri); //todo 这个能获取到内容但获取不到文件属性如名字、路径等
//            File file = new File();
//            Log.e("TAG","File Name: "+file.getName());
//            Log.e("TAG","File Path: "+file.getAbsolutePath());
//            Log.e("TAG","File Size: "+file.length());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void launch(View view){
        picturesPicker.launch("image/*");
    }
}