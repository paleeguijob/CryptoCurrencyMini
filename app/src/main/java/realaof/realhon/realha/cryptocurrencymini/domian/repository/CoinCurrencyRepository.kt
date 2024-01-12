package realaof.realhon.realha.cryptocurrencymini.domian.repository

import kotlinx.coroutines.flow.Flow
import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency

interface CoinCurrencyRepository {

    suspend fun getCoinList(
        limit: Int, offset: Int
    ): Flow<NetworkResponse<CoinCurrency, BaseCommonError>>

    suspend fun searchCoin(keyword: String): Flow<NetworkResponse<CoinCurrency, BaseCommonError>>

    suspend fun getCoinDetail(uuid: String): Flow<NetworkResponse<CoinDetail, BaseCommonError>>
}