package realaof.realhon.realha.cryptocurrencymini.domian.mapper

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Malachite
import realaof.realhon.realha.cryptocurrencymini.ui.theme.RedOrange
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_2DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_2DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.orZero
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyCurrency
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat
import javax.inject.Inject
import kotlin.math.abs

class CoinCurrencyMapperImp @Inject constructor() : CoinCurrencyMapper {

    override fun mapToCoinLandingUi(coinCurrency: CoinCurrency): LandingUiState.LandingUi =
        LandingUiState.LandingUi(
            topPicks = mapToTopPicks(coinCurrency.data.coins),
            coins = mapToCoinList(coinCurrency.data.coins)
        )

    override fun mapToCoinDetailUi(coinDetail: CoinDetail): CoinDetailUiState.CoinDetailUi =
        CoinDetailUiState.CoinDetailUi(
            header = mapToCoinDetailHeader(
                coinDetail.data.coin
                    ?: realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Coin()
            ),
            detail = coinDetail.data.coin?.description.orEmpty(),
            coinWebUrl = coinDetail.data.coin?.websiteUrl.orEmpty()
        )

    override fun mapToCoinListUi(coins: List<Coin>): MutableList<LandingUiState.LandingUi.CoinUi> =
        coins.map { coin -> setCoin(coin) }.toMutableList()

    private fun mapToTopPicks(coins: List<Coin>) = coins
        .filter { it.rank in 1..3 }
        .map { coin -> setCoin(coin) }.toMutableList()

    private fun mapToCoinList(coins: List<Coin>) = coins
        .map { coin -> setCoin(coin) }.toMutableList()

    private fun setCoin(coin: Coin): LandingUiState.LandingUi.CoinUi =
        LandingUiState.LandingUi.CoinUi(
            uuid = coin.uuid.orEmpty(),
            iconUrl = coin.iconUrl.orEmpty(),
            name = coin.name.orEmpty(),
            price = coin.price.toMoneyFormat(NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL),
            symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                symbol = coin.symbol.orEmpty(),
                color = try {
                    Color(coin.color?.toColorInt() ?: "#000000".toColorInt())
                } catch (e: Throwable) {
                    //when cannot convert hex to Color ex. #000
                    Color("#000000".toColorInt())
                }
            ),
            change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                arrowIcon = if (coin.change.orZero().toFloat()
                        .orZero() < 0.0
                ) R.drawable.ic_arrow_down
                else R.drawable.ic_arrow_up,
                change = abs(coin.change.orZero().toFloat().orZero()).toMoneyFormat(
                    NUMBER_WITH_2DECIMAL
                ),
                color = if (coin.change.orZero().toFloat().orZero() < 0.0) RedOrange else Malachite
            )
        )

    private fun mapToCoinDetailHeader(
        coin: realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Coin
    ): CoinDetailUiState.CoinDetailUi.HeaderUi =
        CoinDetailUiState.CoinDetailUi.HeaderUi(
            coinUrl = coin.iconUrl.orEmpty(),
            symbol = CoinDetailUiState.CoinDetailUi.HeaderUi.CoinSymbol(
                symbol = coin.symbol.orEmpty(),
                color = try {
                    Color(coin.color?.toColorInt() ?: "#000000".toColorInt())
                } catch (e: Throwable) {
                    //when cannot convert hex to Color ex. #000
                    Color("#000000".toColorInt())
                }
            ),
            price = coin.price.toMoneyFormat(NUMBER_WITH_COMMA_AND_DOLLAR_2DECIMAL),
            marketCap = coin.marketCap.toMoneyCurrency()
        )
}