package com.xuebinduan.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.xuebinduan.retrofit.GlobalConstants.NET_MSG_TAG;

public class ShowRawBodyInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        ResponseBody body = response.body();
        if (body != null) {
            //这个显示没有格式化的数据主要是为了方便复制信息
            Log.w(NET_MSG_TAG, "response body (方便复制信息): ");
            Log.w(NET_MSG_TAG, body.string());
        }
        return response;
    }
}
