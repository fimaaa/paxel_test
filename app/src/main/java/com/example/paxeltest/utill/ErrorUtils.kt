package com.example.paxeltest.utill

import com.google.gson.JsonParser
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object ErrorUtils {
    fun getErrorThrowableMsg(error: Throwable, content: String = "Data"): String = when (error) {
        is HttpException ->
            try {
                val errorJsonString = error.response()?.errorBody()?.string()
                JsonParser.parseString(errorJsonString)
                    .asJsonObject["message"]
                    .asString
            } catch (e: Exception) {
                when (error.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> "Tidak dapat mengakses data"
                    HttpsURLConnection.HTTP_NOT_FOUND, HttpsURLConnection.HTTP_NO_CONTENT -> content
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> "Terjadi gangguan pada server"
                    HttpsURLConnection.HTTP_BAD_REQUEST -> "Data tidak sesuai"
                    HttpsURLConnection.HTTP_FORBIDDEN -> "Sesi telah berakhir"
                    429 -> "Terlalu Banyak Request"
                    else -> "Oops, Terjadi gangguan, coba lagi beberapa saat"
                }
            }
        is UnknownHostException -> "Tidak ada koneksi internet"
        is ConnectException -> "No internet connected"
        is SocketTimeoutException -> "No internet connected"
        is Errors.OfflineException -> "No internet connected"
        is Errors.FetchException -> "Fetch exception"
        else -> "Terjadi kesalahan"
    }

    fun getErrorThrowableCode(error: Throwable?): Int = when (error) {
        is HttpException ->
            try {
                val errorJsonString = error.response()?.errorBody()?.string()
                JsonParser.parseString(errorJsonString)
                    .asJsonObject["statusCode"]
                    .asInt
            } catch (e: Exception) {
                when (error.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> 401
                    HttpsURLConnection.HTTP_NOT_FOUND -> 404
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> 500
                    HttpsURLConnection.HTTP_BAD_REQUEST -> 400
                    HttpsURLConnection.HTTP_FORBIDDEN -> 403
                    HttpsURLConnection.HTTP_CONFLICT -> 409
                    else -> error.code()
                }
            }
        else -> 500
    }

    sealed class Errors
        (msg: String) : Exception(msg) {
        class OfflineException(msg: String = "Not Connected to Internet") : Errors(msg)
        class FetchException(msg: String) : Errors(msg)
    }
}