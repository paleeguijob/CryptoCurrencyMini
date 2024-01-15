package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Alabaster
import realaof.realhon.realha.cryptocurrencymini.ui.theme.DustyGray
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Malachite
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@Composable
fun CoinTopItem(
    modifier: Modifier = Modifier,
    coinUi: LandingUiState.LandingUi.CoinUi,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Alabaster
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimen.dimen_2
        ),
        modifier = modifier
            .widthIn(max = dimen.dimen_125)
            .clickable {
                onClickedItem(coinUi)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen.dimen_16)
        ) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coinUi.iconUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Coin Top Icon",
                modifier = Modifier
                    .size(dimen.dimen_40)
            )

            Text(
                text = coinUi.symbol.symbol,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = coinUi.symbol.color
                ),
                modifier = Modifier
                    .padding(vertical = dimen.dimen_8)
                    .clearAndSetSemantics {
                        contentDescription = "Coin Name: ${coinUi.symbol.symbol}"
                    }
            )
            Text(
                text = coinUi.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = DustyGray
                ),
                modifier = Modifier
                    .padding(bottom = dimen.dimen_8)
                    .clearAndSetSemantics {
                        contentDescription = "Full Coin Name: ${coinUi.name}"
                    }
            )
            TextWithTrend(changeUi = coinUi.change)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinTopItemPreview() {
    CoinTopItem(
        coinUi = LandingUiState.LandingUi.CoinUi(
            iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
            name = "Bitcoin",
            price = "",
            symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                symbol = "BTC",
                color = Color("#f7931A".toColorInt())
            ),
            change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                change = "1.8",
                color = Malachite
            )
        )
    )
}