package realaof.realhon.realha.cryptocurrencymini.base.network

import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        if (Call::class.java != getRawType(returnType))
            throw Throwable(NOT_PARAMETERIZED_TYPE_ERROR_MESSAGE)

        check(returnType is ParameterizedType) { NOT_PARAMETERIZED_TYPE_ERROR_MESSAGE }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResponse::class.java)
            throw Throwable(NOT_PARAMETERIZED_TYPE_ERROR_MESSAGE)

        check(responseType is ParameterizedType) { NOT_PARAMETERIZED_TYPE_ERROR_MESSAGE }

        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return NetworkResponseAdapter<Any, Any>(
            successBodyType,
            errorBodyConverter
        )
    }

    companion object {
        private const val NOT_PARAMETERIZED_TYPE_ERROR_MESSAGE =
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
    }
}