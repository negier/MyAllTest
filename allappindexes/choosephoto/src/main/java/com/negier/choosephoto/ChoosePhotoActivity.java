package com.negier.choosephoto;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ChoosePhotoActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 999;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1000;
    private ChoosePhoto mChoosePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        mChoosePhoto = new ChoosePhoto(this, "com.negier.choosephoto.fileprovider");
        initListener();
    }

    private void initListener() {
        //拍摄
        findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ChoosePhotoActivity.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    //未授权
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(ChoosePhotoActivity.this,Manifest.permission.CAMERA)){
                        //用户在授权面板对这个危险权限点击了“don't ask again”
                        showDialogJumpSetting();
                    }else {
                        ActivityCompat.requestPermissions(ChoosePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                }else{
                    //已授权
                    mChoosePhoto.openCamera();
                }
            }
        });

        //选择照片
        findViewById(R.id.choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ChoosePhotoActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    //未授权
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(ChoosePhotoActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        //用户在授权面板对这个危险权限点击了“don't ask again”
                        showDialogJumpSetting();
                    }else {
                        ActivityCompat.requestPermissions(ChoosePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                    }
                }else{
                    //已授权
                    mChoosePhoto.openAlbum();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath = mChoosePhoto.onResult(requestCode,resultCode,data);
        if (!TextUtils.isEmpty(imagePath)){
            Log.e("ChoosePhotoActivity","图片地址："+imagePath);
            Intent intent = new Intent();
            intent.putExtra("imagePath",imagePath);
            setResult(resultCode,intent);
            finish();
        }
    }

    public void showDialogJumpSetting(){
        new AlertDialog.Builder(ChoosePhotoActivity.this)
                .setTitle("警告")
                .setMessage("您需要去应用设置开启权限，才能正常使用此功能")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public void showDialogHint(String hint,DialogInterface.OnClickListener onPositiveClickListener){
        new AlertDialog.Builder(ChoosePhotoActivity.this)
                .setTitle("警告")
                .setMessage(hint)
                .setPositiveButton("确定", onPositiveClickListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    ////////////////////////////////权限////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //已授权
                    mChoosePhoto.openCamera();
                }else{
                    //权限被拒绝，禁止基于这个权限的功能
                    showDialogHint("您需要授予我这个权限才能正常使用此功能", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(ChoosePhotoActivity.this,
                                    new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
                        }
                    });
                }
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //已授权
                    mChoosePhoto.openAlbum();
                }else{
                    //权限被拒绝，禁止基于这个权限的功能
                    showDialogHint("您需要授予我这个权限才能正常使用此功能",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(ChoosePhotoActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                    ,REQUEST_CAMERA_PERMISSION);
                        }
                    });
                }
                break;
        }
    }
}
