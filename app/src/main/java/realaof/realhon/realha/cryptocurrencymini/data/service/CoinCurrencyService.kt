package realaof.realhon.realha.cryptocurrencymini.data.service

import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.base.network.NetworkConfigs.BASE_TOKEN
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinCurrencyService {

    @GET("v2/coins")
    suspend fun getCoinList(
        @Header("x-access-token") token: String = BASE_TOKEN,
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 0
    ): NetworkResponse<CoinCurrency, BaseCommonError>

    @GET("v2/coins")
    suspend fun searchCoin(
        @Header("x-access-token") token: String = BASE_TOKEN,
        @Query("search") keyword: String,
    ): NetworkResponse<CoinCurrency, BaseCommonError>

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetail(
        @Header("x-access-token") token: String = BASE_TOKEN,
        @Path("uuid") uuid: String
    ): NetworkResponse<CoinDetail, BaseCommonError>
}