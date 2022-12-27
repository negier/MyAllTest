#include <jni.h>
#include <android/log.h>

extern "C" {
jint gainNumber(JNIEnv *env) {
    return 10;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    jclass clazz = env->FindClass("com/xuebinduan/dynamicjni/SecondActivity");
    if (clazz == NULL) {
        return JNI_ERR;
    }

    JNINativeMethod methods_SecondActivity[] = {
            {"gainNumber", "()I", (void *) gainNumber}
    };
    jint result = env->RegisterNatives(clazz, methods_SecondActivity,
                                       sizeof(methods_SecondActivity) /
                                       sizeof(methods_SecondActivity[0]));
    if (result < 0) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}
}

