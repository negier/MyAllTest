package com.example.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;


/**
 * Camera文档：
 * https://developer.android.com/guide/topics/media/camera#java
 */
public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CAMERA_PERMISSION = 1 << 1;
    private Camera mainCamera;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Camera相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //未授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            //已授权
            init();
        }

    }

    private void init() {
        surfaceView = findViewById(R.id.surface_view);

        //检查是否有摄像头
        if (!checkCameraHardware(this)) {
            android.widget.Toast.makeText(this, "此设备没有摄像头", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("TAG", "可用摄像头数量为：" + Camera.getNumberOfCameras());
        //访问主摄像头
        mainCamera = Camera.open();

        //设置摄像头方向
        mainCamera.setDisplayOrientation(90);

        //打印摄像头相关信息
        Camera.Parameters parameters = mainCamera.getParameters();
        // - 如果你要Parameters#setPreviewSize()，值应该是往这里面拿才行；
        // - 更多的Parameters设置，如人脸检测呀、高光呀、对焦呀，在 https://developer.android.com/guide/topics/media/camera#camera-features
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        printSupportedPreviewSizes(supportedPreviewSizes);
        Camera.Size size = findMostPixelSize(supportedPreviewSizes);
        parameters.setPreviewSize(size.width, size.height);
        mainCamera.setParameters(parameters);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new MySurfaceHolderCallback());
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mainCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                // 默认是YCbCr_420_SP (NV21)，ImageFormat#NV21。可以通过Parameters#getPreviewFormat()获取，也可以通过Parameters#setPreviewFormat()设置。
                if (data.length > 0) {
                    int previewFormat = mainCamera.getParameters().getPreviewFormat();
                    Log.e("TAG","previewFormat:"+previewFormat);
                }
            }
        });
    }

    /**
     * 找一个像素最多，精度最多的Size
     *
     * @param supportedPreviewSizes
     * @return
     */
    private Camera.Size findMostPixelSize(List<Camera.Size> supportedPreviewSizes) {
        Camera.Size maxSize = supportedPreviewSizes.get(0);
        for (int i = 1; i < supportedPreviewSizes.size(); i++) {
            Camera.Size size = supportedPreviewSizes.get(i);
            if ((size.width + size.height) > (maxSize.width + maxSize.height)) {
                maxSize = size;
            }
        }
        return maxSize;
    }

    private class MySurfaceHolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            beginPreview(holder);
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            // empty. Take care of releasing the Camera preview in your activity.
            // 设置自动对焦
            mainCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        Camera.Parameters parameters = mainCamera.getParameters();
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        mainCamera.setParameters(parameters);
                    }
                }
            });
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            try {
                mainCamera.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }
            beginPreview(holder);
        }
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private void beginPreview(@NonNull SurfaceHolder holder) {
        try {
            mainCamera.setPreviewDisplay(holder);
            mainCamera.startPreview();
        } catch (IOException e) {
            Log.e("TAG", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已授权
                    init();
                } else {
                    //权限请求被拒绝
                    //todo 可弹出面板告知用户需要权限，让用户选择是否授权，不要在这里粗鲁的直接再次请求权限
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Log.e("TAG", "暂时权限拒绝");
                    } else {
                        Log.e("TAG", "永久权限拒绝");
                    }
                }
        }
    }

    private void printSupportedPreviewSizes(List<Camera.Size> supportedPreviewSizes) {
        for (Camera.Size size : supportedPreviewSizes) {
            Log.e("TAG", "size:" + size.width + "x" + size.height);
        }
    }

    @Override
    protected void onDestroy() {
        mainCamera.release();
        super.onDestroy();
    }


}