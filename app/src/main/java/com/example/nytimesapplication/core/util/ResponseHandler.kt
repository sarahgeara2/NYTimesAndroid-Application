package com.example.nytimesapplication.core.util

import com.example.nytimesapplication.common.ErrorMessage
import com.example.nytimesapplication.common.GlobalFunctions
import com.example.nytimesapplication.core.vo.Resource
import com.google.gson.Gson
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject


open class ResponseHandler @Inject constructor() {
    fun <T : Any> handleSuccess(data: T?): Resource<T> {
        GlobalFunctions.printLn("handleSuccess>>>>>>>>>>>>>>>>>>>>>>>>>>")
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        try {
            GlobalFunctions.printLn("handleException>>>>>>>>>>>>>>>>>>>>>>>>>>" + e)
            e.printStackTrace()
            return when (e) {
                //no need to show error for NotConnectedException as it is already handled ("@NoInternetConnection annotation)
                is CancellationException -> Resource.canceled("Canceled")
                is NotConnectedException -> Resource.error("", null)
                is HttpException -> Resource.error(
                    convertErrorBody(e).message,
                    null
                )
                else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        } catch (e: Exception) {
            GlobalFunctions.printException(e)
            return Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorMessage {
        var error =
            ErrorMessage(true, "Something went wrong, Please try again")
//        var errorResponse = ErrorResponse(error)
        return try {
            var json = throwable.response()?.errorBody()?.string()
            GlobalFunctions.printLn("throwable>>>>>>>>>>response>>>>>>>>" + throwable.response())
            GlobalFunctions.printLn("throwable>>>>>>>>errorBody>>>>>>>>>" + throwable.response()?.errorBody())
            GlobalFunctions.printLn("throwable>>>>>>>>json>>>>>>>>>" + json)
            val gson = Gson()
            GlobalFunctions.printLn("throwable>>>>>>>>gson>>>>>>>>>" + gson)
            gson.fromJson(json, ErrorMessage::class.java)
        } catch (exception: Exception) {
            exception.printStackTrace()
            GlobalFunctions.printLn("throwable>>>>>>>>exception>>>>>>>>>>>>>>>>>>>>${exception.message}")
            error
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
//            401 -> "Unauthorized"
//            404 -> "Not found"
            else -> "Something went wrong, Please try again"
        }
    }
}