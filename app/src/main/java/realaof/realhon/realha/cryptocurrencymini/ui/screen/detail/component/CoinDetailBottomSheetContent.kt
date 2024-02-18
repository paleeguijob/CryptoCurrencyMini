package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.DustyGray
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SearchBg
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SkyLight
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@Composable
fun CoinDetailBottomSheetContent(
    modifier: Modifier = Modifier,
    coinDetailUi: CoinDetailUiState.CoinDetailUi,
    onClickedWebsiteButton: (webUrl: String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = dimen.dimen_24,
                vertical = dimen.dimen_16
            )
    ) {
        CoinDetailHeader(headerUi = coinDetailUi.header)
        Spacer(modifier = Modifier.height(dimen.dimen_16))
        Text(
            text = coinDetailUi.detail,
            style = MaterialTheme.typography.titleMedium.copy(
                color = DustyGray
            ),
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider(color = SearchBg)
        Text(
            text = stringResource(id = R.string.coin_currency_detail_coin_detail_button_text_go_to_web_site),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = SkyLight,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen.dimen_16)
                .clickable { onClickedWebsiteButton(coinDetailUi.coinWebUrl) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinBottomSheetDetailContentPreview() {
    CoinDetailBottomSheetContent(
        coinDetailUi = CoinDetailUiState.CoinDetailUi(
            header = CoinDetailUiState.CoinDetailUi.HeaderUi(
                coinUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                name = "BitCoin",
                symbol = CoinDetailUiState.CoinDetailUi.HeaderUi.CoinSymbol(
                    symbol = "BTC",
                    color = Orange
                ),
                price = "45973.474499812466".toMoneyFormat(
                    NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
                ),
                marketCap = "900819752410"

            ),
            detail = "Coin Currency".repeat(20),
            coinWebUrl = "https://bitcoin.org"
        ),
        onClickedWebsiteButton = {}
    )
}