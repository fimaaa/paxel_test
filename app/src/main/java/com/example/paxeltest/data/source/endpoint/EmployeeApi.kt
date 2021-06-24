package com.example.paxeltest.data.source.endpoint

import com.example.paxeltest.data.base.BaseResponse
import com.example.paxeltest.data.model.Employee
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {

    @GET("employees")
    suspend fun getEmployeesData(
//        @Path("search") search: String?
    ): BaseResponse<MutableList<Employee.Data>>

    @GET("employee/{search}")
    suspend fun getEmployeeData(
        @Path("search") search: String?
    ): BaseResponse<Employee.Data>
}