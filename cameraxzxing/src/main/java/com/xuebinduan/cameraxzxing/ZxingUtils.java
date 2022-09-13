package com.xuebinduan.cameraxzxing;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.zxing.*;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZxingUtils {
    private static ExecutorService executorService;
    private static Map<DecodeHintType,Object> hints = new HashMap<>();
    static{
        hints.put(DecodeHintType.CHARACTER_SET,"utf-8");
        hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS,BarcodeFormat.QR_CODE);
    }
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
                        // 未识别成功，会报com.google.zxing.NotFoundException，刚好捕获到回调failure()方法。
                        Result result = qrCodeReader.decode(binaryBitmap,hints);
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
