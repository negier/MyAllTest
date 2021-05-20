package com.xuebinduan.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.xuebinduan.retrofit.GlobalConstants.NET_MSG_TAG;

public class ShowRawEntityInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        ResponseBody body = response.body();
        Log.e(NET_MSG_TAG,"body:"+body.string());
        return response;
    }
}
