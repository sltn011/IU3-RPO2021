#include <jni.h>
#include <string>
#include <memory>

#include "android/log.h"
#include "spdlog/spdlog.h"
#include "spdlog/sinks/android_sink.h"

#define LOG_INFO(...) __android_log_print(ANDROID_LOG_INFO, "fclient_ndk", __VA_ARGS__)

auto androidLogger = spdlog::android_logger_mt("android spdlogger", "fclient_ndk");
#define SLOG_INFO(...) androidLogger->info(__VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_ru_iu3_labs_fclient_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from fclient";
    LOG_INFO("Hello from android logger %d", 2021);
    SLOG_INFO("Hello from spdlog {}", 2021);
    return env->NewStringUTF(hello.c_str());
}



#include "mbedtls/entropy.h"
#include "mbedtls/ctr_drbg.h"

mbedtls_entropy_context entropy;
mbedtls_ctr_drbg_context ctrDrbgContext;
std::string personalization = "fclient-sample-app";

extern "C" JNIEXPORT jint JNICALL
Java_ru_iu3_labs_fclient_MainActivity_initRng(
        JNIEnv *env,
        jclass clazz ) {
    mbedtls_entropy_init(&entropy);
    mbedtls_ctr_drbg_init(&ctrDrbgContext);
    return mbedtls_ctr_drbg_seed(
            &ctrDrbgContext, mbedtls_entropy_func, &entropy,
            reinterpret_cast<const unsigned char*>(personalization.c_str()),
            personalization.size()
    );
}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_ru_iu3_labs_fclient_MainActivity_randomBytes(
        JNIEnv *env,
        jclass clazz,
        jint n ) {
    std::unique_ptr<uint8_t[]> buf = std::make_unique<uint8_t[]>(n);
    mbedtls_ctr_drbg_random(&ctrDrbgContext, buf.get(), n);
    jbyteArray rnd = env->NewByteArray(n);
    env->SetByteArrayRegion(rnd, 0, n, reinterpret_cast<jbyte*>(buf.get()));
    return rnd;
}