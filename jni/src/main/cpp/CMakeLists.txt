cmake_minimum_required(VERSION 3.4.1)

add_library(
        mytest

        SHARED

        mytest.c)

add_library(
        mytestdanamic

        SHARED

        mytest_danamic.cpp)

target_link_libraries(mytestdanamic
        log)