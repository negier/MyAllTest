package com.xuebinduan.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WebService {
    @GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String): User

    //演示传json在body里，就这样写
    @POST()
    suspend fun getList(@Body body: QueryBean):NetworkResult<String>

}