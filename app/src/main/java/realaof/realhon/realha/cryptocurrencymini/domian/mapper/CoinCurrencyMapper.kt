package realaof.realhon.realha.cryptocurrencymini.domian.mapper

import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState

interface CoinCurrencyMapper {

    fun mapToCoinLandingUi(coinCurrency: CoinCurrency): LandingUiState.LandingUi

    fun mapToCoinDetailUi(coinDetail: CoinDetail): CoinDetailUiState.CoinDetailUi

    fun mapToCoinListUi(coins: List<Coin>) : MutableList<LandingUiState.LandingUi.CoinUi>
}