#include <jni.h>

JNIEXPORT jint JNICALL Java_com_xuebinduan_jni_MainActivity_gainNumber(JNIEnv *env,jobject obj){
    // C访问Java中的函数：
    jclass cls = (*env)->GetObjectClass(env,obj); //获取java对象的类
    jmethodID mid = (*env)->GetMethodID(env,cls,"printSome","()V"); //获取java函数的id值，todo 方法签名说明需要时可以查资料，括号里是方法参数，括号外是返回值V表示void的意思
    (*env)->CallVoidMethod(env,obj,mid);//调用函数

    // C访问Java中的变量：
//    jclass cls = (*env)->GetObjectClass(env,obj); //获取java对象的类
    jfieldID fid = (*env)->GetFieldID(env,cls,"number","I"); //获取变量的id值
    jint value = (*env)->GetIntField(env,obj,fid);//获取变量值
    return value;
}

