package com.example.paxeltest.di

import com.example.paxeltest.BuildConfig
import com.example.paxeltest.data.enum.EnumBuild

external fun getDebugBaseUrl(): String
external fun getStagingBaseUrl(): String
external fun getReleaseBaseUrl(): String

object ExternalData {
    init {
        System.loadLibrary("native-lib")
    }
}

fun getBaseUrl(): String =
    when (BuildConfig.VARIANT) {
        EnumBuild.RELEASE.name -> getReleaseBaseUrl()
        EnumBuild.STAGING.name -> getStagingBaseUrl()
        else -> getDebugBaseUrl()
    }