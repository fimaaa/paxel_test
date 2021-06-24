package com.example.paxeltest.data.source.endpoint

import com.example.paxeltest.data.base.BaseResponse
import com.example.paxeltest.data.model.Employee
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeApi {

    @GET("employees")
    suspend fun getExampleDataSuccess(): MutableList<Employee.Data>

    @GET("users")
    suspend fun getExampleDataFailed(
        @Query("since") since: Int
    ): BaseResponse<MutableList<Employee.Data>>
}