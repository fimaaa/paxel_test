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
    private val employeeApi: EmployeeApi
) : PagingSource<Int, Employee.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee.Data> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = employeeApi.getExampleDataSuccess()
            LoadResult.Page(
                data = response,
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
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Employee.Data>): Int = STARTING_PAGE_INDEX
}