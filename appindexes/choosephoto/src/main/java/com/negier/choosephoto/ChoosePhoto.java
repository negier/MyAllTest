package com.negier.choosephoto;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * ==========================================================
 * <p>
 * 功能描述：
 * ChoosePhoto.class
 * 两个功能：
 * 拍照
 * 选择相册
 * 需要留意的：
 * AndroidManifest.xml的authorities的值
 *
 * @author： NEGIER
 * @date： 2017/10/25 17:36
 * <p>
 * ==========================================================
 */

public class ChoosePhoto {
    private final Context context;
    /**
     * 照相后的照片是否裁剪，默认true
     */
    private boolean isCrop;
    /**
     * requestCode:照相
     */
    public static final int TAKE_PHOTO = 1;
    /**
     * requestCode:相册选择
     */
    public static final int CHOOSE_PHOTO = 2;
    /**
     * requestCode:相片裁剪
     */
    public static final int PHOTO_CROP = 3;
    /**
     * xml>file_paths.xml的目录文件
     * new File(Environment.getExternalStorageDirectory()+"/[path]"+"/"+"[要保存的文件名 default:avator]")
     */
    private File file;
    /**
     * 从file获取到的uri
     */
    private Uri fileUri;
    /**
     * 图片的MIME类型
     */
    private static final String IMAGE_UNSPECIFIED = "image/*";
    /**
     * 剪切后的图片宽度比例
     */
    private int aspectX = 1;
    /**
     * 剪切后的图片高度比例
     */
    private int aspectY = 1;
    /**
     * 剪切后的图片宽度
     */
    private int outputX = 130;
    /**
     * 剪切后的图片高度
     */
    private int outputY = 130;
    /**
     * 在AndroidManifest.xml中定义的provider的authorities
     * 值一般为：context.getPackageName()+".fileprovider"
     */
    private String authority;

    /**
     * 构造方法
     *
     * @param context Context
     */
    public ChoosePhoto(Context context, String authority) {
        this.context = context;
        this.authority = authority;
        file = getDefaultFile();
    }

    /**
     * 构造方法
     *
     * @param context Context
     * @param isCrop  是否剪切，默认false
     */
    public ChoosePhoto(Context context, boolean isCrop, String authority) {
        this.context = context;
        this.isCrop = isCrop;
        this.authority = authority;
    }

    /**
     * 构造方法
     *
     * @param context Context
     * @param file    创建File对象，用于存储拍照后的图片
     * @param isCrop  是否剪切，默认false
     */
    public ChoosePhoto(Context context, File file, boolean isCrop, String authority) {
        if (!file.exists()) {
            file.mkdirs();
        }
        this.context = context;
        this.file = file;
        this.isCrop = isCrop;
        this.authority = authority;
    }

    /**
     * 设置是否裁剪
     *
     * @param crop
     */
    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    /**
     * 获取是否要裁剪
     *
     * @return
     */
    public boolean isCrop() {
        return isCrop;
    }

    /**
     * 拍照
     */
    public void openCamera() {
        file = getDefaultFile();
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断是否有相机应用
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Android 7.0 兼容处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fileUri = FileProvider.getUriForFile(context, authority, file);
                } else {
                    fileUri = Uri.fromFile(file);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                ((Activity) context).startActivityForResult(intent, TAKE_PHOTO);
            } else {
                Toast.makeText(context, "存储卡不可用，请从相册选取", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * Activity里回掉此方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public String onResult(int requestCode, int resultCode, Intent data) {
        String imagePath = "";
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (isCrop) {
                        startPhotoZoom(fileUri);
                    } else {
                        imagePath = file.getAbsolutePath();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imagePath = getPath(uri);
//                    //判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        //4.4及以上系统使用这个方法处理图片
//                        imagePath = handleImageBeforeKitKat(data);
//                    } else {
//                        //4.4以下系统使用这个方法处理图片
//                        imagePath = handleImageOnKitKat(data);
//                    }
                }
                break;
            case PHOTO_CROP:
                if (resultCode == RESULT_OK) {
                    saveImage(getBitmapFromIntent(data));
                    imagePath = file.getAbsolutePath();
                }
                break;
            default:
        }
        return imagePath;
    }

    /**
     * 调用系统剪切程序剪切图片
     *
     * @param uri 图片的Uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, PHOTO_CROP);
    }

    /**
     * 从Intent中解析出一个Bitmap实例
     *
     * @param data
     * @return
     */
    private Bitmap getBitmapFromIntent(Intent data) {
        if (data == null) {
            return null;
        }
        Bitmap bitmap = null;
        Bundle extras = data.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
        }
        return bitmap;
    }

    /**
     * 将图片存储在本地
     *
     * @param bitmap
     */
    private void saveImage(Bitmap bitmap) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ChoosePhoto默认的File
     */
    public File getDefaultFile() {
        // 默认的保存路径
        String savePath = Environment.getExternalStorageDirectory() + "/YourFolder" + "/" + "avator"+System.currentTimeMillis()+".jpg";
        File imageFile = new File(savePath);
        if (!imageFile.getParentFile().exists()) {
            imageFile.getParentFile().mkdir();
        }
        return imageFile;
    }


    ////////////////////////////////获取PATH////////////////////////////////

    private String getPath(Uri uri){
        //判断手机系统版本号
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4及以上系统使用这个方法处理图片
            return getPathOnKitKat(uri);
        } else {
            //4.4以下系统使用这个方法处理图片
            return getPathBeforeKitKat(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getPathOnKitKat(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri,则通过document id处理
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                /*
                 * 这是我在华为手机上遇到的一个问题，当时报错NumberFormatException，那我知道肯定是
                 * id的问题，然后一看，id长这样raw:/storage/emulated/0/Download/browser/63627676619
                 * 49948789276468.xlsx，raw:后面的就是真实的路径，所以，直接去掉raw:然后返回就好了。
                 */
                if (id.startsWith("raw:")) {
                    final String path = id.replaceFirst("raw:", "");
                    return path;
                }
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String getPathBeforeKitKat(Uri uri) {
        String imagePath = getDataColumn(context, uri, null, null);
        return imagePath;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
