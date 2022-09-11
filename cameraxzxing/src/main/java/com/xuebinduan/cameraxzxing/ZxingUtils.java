package com.xuebinduan.cameraxzxing;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.zxing.*;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZxingUtils {
    private static ExecutorService executorService;
    public static void decode(Bitmap bitmap,DecodeCallback callback){
        if (executorService==null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (bitmap!=null){
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels,0,width,0,0,width,height);
                    RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(width,height,pixels);
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(rgbLuminanceSource));
                    QRCodeReader qrCodeReader = new QRCodeReader();
                    try {
                        // 未识别成功，会报com.google.zxing.NotFoundException
                        Result result = qrCodeReader.decode(binaryBitmap);
                        if (callback!=null){
                            callback.success(result);
                            executorService.shutdown();
                            executorService = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (callback!=null){
                            callback.failure();
                        }
                    }
                }
            }
        });
    }

    public interface DecodeCallback {
        void success(Result result);
        void failure();
    }

}
