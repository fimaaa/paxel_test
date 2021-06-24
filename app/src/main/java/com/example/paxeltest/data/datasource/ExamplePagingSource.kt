package com.example.paxeltest.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paxeltest.data.model.Employee
import com.example.paxeltest.data.source.endpoint.EmployeeApi
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

class ExamplePagingSource(
    private val employeeApi: EmployeeApi,
    private val search: String?
) : PagingSource<Int, Employee.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee.Data> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val list = mutableListOf<Employee.Data>()
            if (search.isNullOrEmpty()) {
                val response = employeeApi.getEmployeesData()
                list.addAll(response.data)
            } else {
                val response = employeeApi.getEmployeeData(search)
                list.add(response.data)
            }
            // Show only 20 data because no pagination
            if (list.size >= 20) list.subList(20, list.size).clear()
            LoadResult.Page(
                data = list,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
//                nextKey = if (response.isNullOrEmpty()) null else position + 1
                nextKey = null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: JsonSyntaxException) {
            LoadResult.Error(e)
        } catch (e: IllegalStateException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Employee.Data>): Int = STARTING_PAGE_INDEX
}