package com.xuebinduan.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.xuebinduan.retrofit.GlobalConstants.NET_MSG_TAG;

public class ShowRawBodyInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();

        Log.w(
                NET_MSG_TAG, String.format(
                        "Received response for %s in %.1fms",
                        response.request().url(), (t2 - t1) / 1e6)
        );

        try {
            MediaType contentType = response.body().contentType();
            //todo 这里的body.string()只能被消费一次，调用后流就关闭close了，具体可点进去看源码，为了后续其它拦截器的正常工作，我们需要给其返回一个新的
            String bodyStr = response.body().string();
            if (!bodyStr.isEmpty()) {
                //这个显示没有格式化的数据主要是为了方便复制信息
                Log.w(NET_MSG_TAG, "response body (方便复制信息): ");
                //todo 如果字符串太长，IDE的log打印会截掉后面显示的，其单行最长显示4062个字节，所以我们按每行4000字节截断显示就不会有什么问题了
//                Log.w(NET_MSG_TAG, bodyStr);
                // IF YOU USE IntelliJ IDEA
                print(Log.WARN, NET_MSG_TAG, bodyStr);
            }

            return response.newBuilder().body(ResponseBody.create(contentType, bodyStr)).build();
        }catch (Exception e){
            return response;
        }
    }

    //https://www.jianshu.com/p/a491d843fa19
    /**
     * 打印日志到控制台（解决Android控制台丢失长日志记录）
     *
     * @param priority
     * @param tag
     * @param content
     */
    public void print(int priority, String tag, String content) {
        // 1. 测试控制台最多打印4062个字节，不同情况稍有出入（注意：这里是字节，不是字符！！）
        // 2. 字符串默认字符集编码是utf-8，它是变长编码一个字符用1~4个字节表示
        // 3. 这里字符长度小于1000，即字节长度小于4000，则直接打印，避免执行后续流程，提高性能哈
        if (content.length() < 1000) {
            Log.println(priority, tag, content);
            return;
        }

        // 一次打印的最大字节数
        int maxByteNum = 4000;

        // 字符串转字节数组
        byte[] bytes = content.getBytes();

        // 超出范围直接打印
        if (maxByteNum >= bytes.length) {
            Log.println(priority, tag, content);
            return;
        }

        // 分段打印计数
        int count = 1;

        // 在数组范围内，则循环分段
        while (maxByteNum < bytes.length) {
            // 按字节长度截取字符串
            String subStr = cutStr(bytes, maxByteNum);

            // 打印日志
            String desc = String.format("分段打印(%s):%s", count++, subStr);
            Log.println(priority, tag, desc);

            // 截取出尚未打印字节数组
            bytes = Arrays.copyOfRange(bytes, subStr.getBytes().length, bytes.length);

            // 可根据需求添加一个次数限制，避免有超长日志一直打印
            /*if (count == 10) {
                break;
            }*/
        }

        // 打印剩余部分
        Log.println(priority, tag, String.format("分段打印(%s):%s", count, new String(bytes)));
    }

    /**
     * 按字节长度截取字节数组为字符串
     *
     * @param bytes
     * @param subLength
     * @return
     */
    public String cutStr(byte[] bytes, int subLength) {
        // 边界判断
        if (bytes == null || subLength < 1) {
            return null;
        }

        // 超出范围直接返回
        if (subLength >= bytes.length) {
            return new String(bytes);
        }

        // 复制出定长字节数组，转为字符串
        String subStr = new String(Arrays.copyOf(bytes, subLength));

        // 避免末尾字符是被拆分的，这里减1使字符串保持完整
        return subStr.substring(0, subStr.length() - 1);
    }

}
