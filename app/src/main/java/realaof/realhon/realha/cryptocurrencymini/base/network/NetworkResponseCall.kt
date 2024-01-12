package realaof.realhon.realha.cryptocurrencymini.base.network

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(
                            body?.let { NetworkResponse.Success(it) }
                                ?: NetworkResponse.OtherError(SuccessWithEmptyBody)
                        )
                    )
                } else {
                    var converterException: Throwable? = null
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            converterException = ex
                            null
                        }
                    }

                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(
                            errorBody?.let { NetworkResponse.ApiError(it, code) }
                                ?: NetworkResponse.OtherError(
                                    converterException ?: ApiErrorBodyEmpty
                                )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.OtherError(throwable)
                }

                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() =
        NetworkResponseCall(
            delegate.clone(),
            errorConverter
        )

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

object SuccessWithEmptyBody : Throwable("Api successful but return empty body.")

object ApiErrorBodyEmpty : Throwable("Api failure and return empty body error.")