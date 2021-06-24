package com.example.paxeltest.utill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.paxeltest.data.base.Resource
import com.example.paxeltest.data.enum.Status
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class NetworkBoundResource<ResultType, RequestType>(private val contextProviders: ContextProviders) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                Status.SUCCESS -> {
                    GlobalScope.launch(contextProviders.IO) {
                        saveCallResult(response.data)
                        GlobalScope.launch(contextProviders.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                }
                Status.EMPTY -> {
                    GlobalScope.launch(contextProviders.Main) {
                        result.addSource(loadFromDb()) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                Status.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, response.code, newData)
                    }
                }
                else -> {
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>
    protected abstract fun loadFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    abstract fun createCall(): LiveData<Resource<RequestType>>
    abstract fun saveCallResult(item: RequestType?)
    protected open fun onFetchFailed() {}
}