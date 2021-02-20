cmake_minimum_required(VERSION 3.10.2)

project("Lab1")

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
        spdlog PROPERTIES IMPORTED_LOCATION "D:/VedroidStudioLabs/Lab1/libs/spdlog/build/x86/libspdlog.a"
)

target_include_directories(
        native-lib PUBLIC
        "D:/VedroidStudioLabs/Lab1/libs/spdlog/spdlog/include"
)



target_link_libraries(
        native-lib
        spdlog
        ${log-lib}
)