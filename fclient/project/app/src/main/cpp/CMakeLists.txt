cmake_minimum_required(VERSION 3.10.2)

#set(ABI "armaebi-v7a")
#set(ABI "x86")
#set(ABI "arm64-v8a")
set(ABI "x86_64")

project("fclient")

add_library(
        native-lib
        SHARED
        native-lib.cpp
)

find_library(
        log-lib
        log
)



#SPDLOG
add_definitions(-DSPDLOG_COMPILED_LIB)
add_library(spdlog STATIC IMPORTED)
set_target_properties(
        spdlog PROPERTIES IMPORTED_LOCATION "D:/VedroidStudioLabs/fclient/libs/spdlog/build/${ABI}/libspdlog.a"
)



#MBEDTLS
add_library(mbedcrypto SHARED IMPORTED)
configure_file(
        "D:/VedroidStudioLabs/fclient/libs/mbedtls/build/${ABI}/library/libmbedcrypto.so"
        "${CMAKE_CURRENT_SOURCE_DIR}/../cmakeLibs/${ABI}/libmbedcrypto.so"
        COPYONLY
)
set_target_properties(
        mbedcrypto PROPERTIES IMPORTED_LOCATION "${CMAKE_CURRENT_SOURCE_DIR}/../cmakeLibs/${ABI}/libmbedcrypto.so"
)




target_include_directories(
        native-lib PUBLIC
        "D:/VedroidStudioLabs/fclient/libs/spdlog/spdlog/include"
        "D:/VedroidStudioLabs/fclient/libs/mbedtls/mbedtls/include"
)

target_link_libraries(
        native-lib
        spdlog
        mbedcrypto
        ${log-lib}
)