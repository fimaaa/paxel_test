package com.example.paxeltest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paxeltest.data.datasource.ExamplePagingSource
import com.example.paxeltest.data.model.Employee
import com.example.paxeltest.data.source.dao.EmployeeDao
import com.example.paxeltest.data.source.endpoint.EmployeeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepository @Inject constructor(
    private val exampleDao: EmployeeDao,
    private val movieApi: EmployeeApi
) {
    suspend fun addToFavorite(example: Employee.Data) = exampleDao.addExample(example)
    fun getFavoriteMovies() = exampleDao.getAllExample()
    suspend fun checkMovie(id: String) = exampleDao.checkExample(id)
    suspend fun removeFromFavorite(id: String) {
        exampleDao.removeExample(id)
    }

    fun getExampleDataset() =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { ExamplePagingSource(movieApi) }
        ).liveData
}