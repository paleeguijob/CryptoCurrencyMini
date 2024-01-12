package realaof.realhon.realha.cryptocurrencymini.domian.mapper

import realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency

interface CoinCurrencyMapper {

    fun mapToCoinLandingUi(coinCurrency: CoinCurrency): LandingUiState.LandingUi

    fun mapToCoinDetailUi(coinDetail: CoinDetail): CoinDetailUiState.CoinDetailUi

    fun mapToCoinListUi(coins: List<Coin>) : MutableList<LandingUiState.LandingUi.CoinUi>
}