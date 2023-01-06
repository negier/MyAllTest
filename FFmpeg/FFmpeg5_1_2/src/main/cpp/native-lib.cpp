#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
{
    #include "libavcodec/avcodec.h"
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ffmpeg5_11_12_MainActivity_testFFmpegMsg(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_ERROR,"TAG","%s", av_version_info());
}