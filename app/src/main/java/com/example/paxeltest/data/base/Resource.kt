package com.example.paxeltest.data.base

import com.example.paxeltest.data.enum.Status
import java.net.HttpURLConnection

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?,
    val throwable: Throwable?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null, null)
        }

        fun <T> error(msg: String?, code: Int? = null, data: T? = null, err: Throwable? = null): Resource<T> {
                return Resource(Status.ERROR, data, msg, code, err)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null, null)
        }

        fun <T> empty(): Resource<T> = Resource(Status.EMPTY, null, null, HttpURLConnection.HTTP_NO_CONTENT, null)
    }
}