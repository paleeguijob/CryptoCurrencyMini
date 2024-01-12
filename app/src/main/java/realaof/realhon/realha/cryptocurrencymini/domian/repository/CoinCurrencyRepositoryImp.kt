package realaof.realhon.realha.cryptocurrencymini.domian.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.data.service.CoinCurrencyService
import javax.inject.Inject

class CoinCurrencyRepositoryImp @Inject constructor(
    private val coinCurrencyService: CoinCurrencyService,
    private val dispatcher: CoroutineDispatcher
) : CoinCurrencyRepository {
    override suspend fun getCoinList(
        limit: Int,
        offset: Int
    ): Flow<NetworkResponse<CoinCurrency, BaseCommonError>> =
        withContext(dispatcher) {
            flow {
                emit(coinCurrencyService.getCoinList(limit = limit, offset = offset))
            }.catch { error ->
                emit(NetworkResponse.OtherError(error))
            }
        }

    override suspend fun searchCoin(keyword: String): Flow<NetworkResponse<CoinCurrency, BaseCommonError>> =
        withContext(dispatcher) {
            flow {
                emit(coinCurrencyService.searchCoin(keyword = keyword))
            }.catch { error ->
                emit(NetworkResponse.OtherError(error))
            }
        }

    override suspend fun getCoinDetail(uuid: String): Flow<NetworkResponse<CoinDetail, BaseCommonError>> =
        withContext(dispatcher) {
            flow {
                emit(coinCurrencyService.getCoinDetail(uuid = uuid))
            }.catch { error ->
                emit(NetworkResponse.OtherError(error))
            }
        }
}