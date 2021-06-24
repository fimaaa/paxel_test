package com.example.paxeltest.data.base

import com.example.paxeltest.data.model.Meta
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @SerializedName("status")
    val status: Boolean?,

    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String?,

    @SerializedName("statusCode")
    val statusCode: Int?,

    @SerializedName("meta")
    val meta: Meta?
)