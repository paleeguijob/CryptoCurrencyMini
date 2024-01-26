package realaof.realhon.realha.cryptocurrencymini.domian.usecase

import kotlinx.coroutines.flow.last
import realaof.realhon.realha.cryptocurrencymini.base.model.toDataOrThrow
import realaof.realhon.realha.cryptocurrencymini.base.usecase.BaseSuspendUseCase
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepository
import javax.inject.Inject

class SearchCoinUseCase @Inject constructor(
    private val repository: CoinCurrencyRepository
) : BaseSuspendUseCase<SearchCoinUseCase.Input, CoinCurrency>() {

    override suspend fun create(input: Input): CoinCurrency =
        repository.searchCoin(input.keyword, input.offset).last().toDataOrThrow()

    data class Input(val keyword: String, val offset: Int)
}