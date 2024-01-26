package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@Composable
fun CoinDetailHeader(
    modifier: Modifier = Modifier,
    headerUi: CoinDetailUiState.CoinDetailUi.HeaderUi
) {
    Row(modifier = modifier) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(headerUi.coinUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()
        )
        
        Image(
            painter = painter,
            contentDescription = "Coin Detail Header Icon",
            modifier = Modifier.size(dimen.dimen_50)
        )
        Spacer(modifier = Modifier.width(dimen.dimen_16))
        CoinDetailHeaderContent(headerUi = headerUi)
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailHeaderPreview() {
    CoinDetailHeader(
        headerUi = CoinDetailUiState.CoinDetailUi.HeaderUi(
            coinUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
            name = "BitCoin",
            symbol = CoinDetailUiState.CoinDetailUi.HeaderUi.CoinSymbol(
                symbol = "BTC",
                color = Orange
            ),
            price = "45973.474499812466".toMoneyFormat(NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL),
            marketCap = "900819752410"

        )
    )
}