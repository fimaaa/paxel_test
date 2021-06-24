package com.example.paxeltest.data.source.endpoint

import com.example.paxeltest.data.base.BaseResponse
import com.example.paxeltest.data.model.Example
import com.example.paxeltest.di.getBaseUrl
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleApi {

    @GET("users")
    suspend fun getExampleDataSuccess(
        @Query("since") since: Int
    ): MutableList<Example.Data>

    @GET("users")
    suspend fun getExampleDataFailed(
        @Query("since") since: Int
    ): BaseResponse<MutableList<Example.Data>>
}