package com.challenge.networking.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException

const val UNKNOWN_ERROR_CODE = 900

@Serializable
data class ErrorBody(
    @SerialName("error") val error: ErrorBodyContent = ErrorBodyContent(),
)

@Serializable
data class ErrorBodyContent(
    @SerialName("code") val code: Int = UNKNOWN_ERROR_CODE,
    @SerialName("message") val message: String = "",
)

fun Throwable.toErrorBody(): ErrorBody {
    return if (this is HttpException) {
        val jsonErrorBody = this.response()?.errorBody()?.string().orEmpty()
        val errorBody = try {
            Json.decodeFromString<ErrorBody>(jsonErrorBody)
        } catch (e: Throwable) {
            ErrorBody(
                error = ErrorBodyContent(
                    code = this.code(),
                    message = this.message(),
                )
            )
        }
        errorBody
    } else {
        ErrorBody(
            error = ErrorBodyContent(
                code = UNKNOWN_ERROR_CODE,
                message = this.message.orEmpty(),
            )
        )
    }
}