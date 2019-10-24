package com.saba.sampleMVVM.base.presentation.eventHandling

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

enum class WarningResponse {
    FAIL, UNAUTHORISED, NOT_FOUNT, OFFLINE, POOR_CONNECTION
}

fun Throwable.handleEvent(callback: (WarningResponse) -> Unit) {
    var errorResponse = WarningResponse.FAIL
    if (this is HttpException) {
        if (this.code() == 401) {
            errorResponse = WarningResponse.UNAUTHORISED
        }
        if (this.code() == 404) {
            errorResponse = WarningResponse.NOT_FOUNT
        }
    } else {
        if (this is UnknownHostException) {
            errorResponse = WarningResponse.OFFLINE
        }
        if (this is SocketTimeoutException) {
            errorResponse = WarningResponse.POOR_CONNECTION
        }
    }
    callback.invoke(errorResponse)
}