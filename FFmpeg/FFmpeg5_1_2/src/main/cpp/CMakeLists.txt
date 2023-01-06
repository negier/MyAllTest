cmake_minimum_required(VERSION 3.4.1)

set(JNI_LIBS_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs)

add_library(libavcodec
        SHARED
        IMPORTED )
set_target_properties(libavcodec
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libavcodec.so )

add_library(libavdevice
        SHARED
        IMPORTED )
set_target_properties(libavdevice
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libavdevice.so )

add_library(libavfilter
        SHARED
        IMPORTED )
set_target_properties(libavfilter
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libavfilter.so )

add_library(libavformat
        SHARED
        IMPORTED )
set_target_properties(libavformat
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libavformat.so )

add_library(libswresample
        SHARED
        IMPORTED )
set_target_properties(libswresample
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libswresample.so )

add_library(libavutil
        SHARED
        IMPORTED )
set_target_properties(libavutil
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libavutil.so )

add_library(libswscale
        SHARED
        IMPORTED )
set_target_properties(libswscale
        PROPERTIES IMPORTED_LOCATION
        ${JNI_LIBS_DIR}/${ANDROID_ABI}/libswscale.so )

find_library(
        log-lib
        log)

add_library(native-lib
        SHARED
        native-lib.cpp)

include_directories(${JNI_LIBS_DIR}/includes)

target_link_libraries(native-lib
        libavdevice
        libavfilter
        libavformat
        libswscale
        libavcodec
        libswresample
        libavutil
        ${log-lib})




