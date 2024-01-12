package realaof.realhon.realha.cryptocurrencymini.base.model

import java.io.IOException

sealed class BaseCommonError : Throwable() {

    data class ApiError(
        val httpCode: Int,
        val code: Int,
        val title: String?,
        override val message: String?,
        val payload: Any?
    ) : BaseCommonError()

    data class NetworkError(val exception: IOException) : BaseCommonError()

    data class OtherError(val throwable: Throwable) : BaseCommonError()

    companion object {
        const val DEFAULT_API_ERROR_MESSAGE = "Not implement this type api error."
    }
}

fun Throwable.toBaseCommonError(): BaseCommonError = when (this) {
    is BaseCommonError -> this
    else -> BaseCommonError.OtherError(this)
}