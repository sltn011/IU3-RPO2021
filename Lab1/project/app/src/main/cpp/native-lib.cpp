#include <jni.h>
#include <string>

#include "android/log.h"
#include "spdlog/spdlog.h"
#include "spdlog/sinks/android_sink.h"

#define LOG_INFO(...) __android_log_print(ANDROID_LOG_INFO, "lab1_ndk", __VA_ARGS__)

auto androidLogger = spdlog::android_logger_mt("android spdlogger", "lab1_ndk");
#define SLOG_INFO(...) androidLogger->info(__VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_ru_iu3_labs_Lab1_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    LOG_INFO("Hello from android logger %d", 2021);
    SLOG_INFO("Hello from spdlog {}", 2021);
    return env->NewStringUTF(hello.c_str());
}