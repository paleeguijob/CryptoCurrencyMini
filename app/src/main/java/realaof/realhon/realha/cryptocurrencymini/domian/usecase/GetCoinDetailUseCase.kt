package realaof.realhon.realha.cryptocurrencymini.domian.usecase

import kotlinx.coroutines.flow.last
import realaof.realhon.realha.cryptocurrencymini.base.model.toDataOrThrow
import realaof.realhon.realha.cryptocurrencymini.base.usecase.BaseSuspendUseCase
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinCurrencyRepository
) : BaseSuspendUseCase<GetCoinDetailUseCase.Input, CoinDetail>() {

    override suspend fun create(input: Input): CoinDetail =
        repository.getCoinDetail(input.uuid).last().toDataOrThrow()

    data class Input(val uuid: String)
}