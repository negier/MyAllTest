package com.xuebinduan.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {
    @GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String): User
}