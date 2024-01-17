package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Alabaster
import realaof.realhon.realha.cryptocurrencymini.ui.theme.MineShaft
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@Composable
fun CoinItem(
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
            .fillMaxWidth()
            .clickable {
                onClickedItem(coinUi)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = dimen.dimen_20,
                    horizontal = dimen.dimen_16
                )
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
                contentDescription = "Coin Icon Item",
                modifier = modifier
                    .size(dimen.dimen_40)
            )

            Spacer(modifier = Modifier.width(dimen.dimen_16))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clearAndSetSemantics {
                        contentDescription = "Coin Item Detail"
                    }
            ) {
                CoinTextWithPrice(name = coinUi.name, price = coinUi.price)
                Spacer(modifier = Modifier.height(dimen.dimen_6))
                CoinTextWithTrend(symbolUi = coinUi.symbol, changeUi = coinUi.change)
            }
        }
    }
}

@Composable
private fun CoinTextWithPrice(
    modifier: Modifier = Modifier,
    name: String,
    price: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MineShaft,
                fontWeight = FontWeight.W700
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .clearAndSetSemantics {
                    contentDescription = "Coin Item name: $name"
                }
        )
        Spacer(modifier = Modifier.width(dimen.dimen_8))
        Text(
            text = price,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MineShaft,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
                .clearAndSetSemantics {
                    contentDescription = "Coin Item Price $price"
                }
        )
    }
}

@Composable
fun CoinTextWithTrend(
    modifier: Modifier = Modifier,
    symbolUi: LandingUiState.LandingUi.CoinUi.CoinSymbol,
    changeUi: LandingUiState.LandingUi.CoinUi.ChangeUi
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = symbolUi.symbol,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(symbolUi.color.toColorInt()),
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
                .weight(1f)
                .clearAndSetSemantics {
                    contentDescription = "Coin Text With Change, name: ${symbolUi.symbol}"
                }
        )
        Spacer(modifier = Modifier.width(dimen.dimen_8))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextWithTrend(changeUi = changeUi)
        }
    }
}

@Composable
fun TextWithTrend(
    modifier: Modifier = Modifier,
    changeUi: LandingUiState.LandingUi.CoinUi.ChangeUi
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (changeUi.arrowIcon != null) {
            //show i con when arrow icon != null
            Image(
                painter = painterResource(id = changeUi.arrowIcon),
                contentDescription = "Coin Text With Change Image",
                modifier = Modifier.size(dimen.dimen_12)
            )
        }

        Spacer(modifier = Modifier.width(dimen.dimen_2))
        Text(
            text = changeUi.change,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(changeUi.color.toColorInt()),
                fontWeight = FontWeight.W700,
            ),
            modifier = Modifier.clearAndSetSemantics {
                contentDescription = "Coin Text With Change., Change: ${changeUi.change}"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinItemPreview() {
    CoinItem(
        coinUi = LandingUiState.LandingUi.CoinUi(
            iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
            name = "Bitcoinasdfjaskdfj",
            price = "1000000000.00000",
            symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                symbol = "BTC",
                color = "#f7931A"
            ),
            change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                arrowIcon = R.drawable.ic_arrow_up,
                change = "1.8",
                color = "#000000"
            )
        )
    )
}