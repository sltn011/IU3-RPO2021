#!/bin/bash

#ABI=armaebi-v7a
ABI=x86
#ABI=arm64-v8a
#ABI=x86_64

ANDROID_NDK="D:/VedroidSDK/ndk/21.1.6352462"
TOOL_CHAIN="${ANDROID_NDK}/build/cmake/android.toolchain.cmake"
CMAKE="D:/VedroidSDK/cmake/3.10.2.4988404/bin/cmake.exe"

mkdir -p ${ABI}
cd ${ABI}

${CMAKE} -G Ninja ../../spdlog -DCMAKE_SYSTEM_NAME=Android -DCMAKE_SYSTEM_VERSION=21 \
-DANDROID_ABI=${ABI} -DCMAKE_TOOLCHAIN_FILE=${TOOL_CHAIN} -DCMAKE_MAKE_PROGRAM=ninja.exe

${CMAKE} --build .