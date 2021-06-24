//
// Created by Fiqri Malik Abdul Az on 5/28/2021.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_paxeltest_di_ExternalDataKt_getStagingBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_paxeltest_di_ExternalDataKt_getReleaseBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_paxeltest_di_ExternalDataKt_getDebugBaseUrl__(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}