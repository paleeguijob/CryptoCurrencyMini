package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item.CoinTopItem
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item.TextInline
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Malachite
import realaof.realhon.realha.cryptocurrencymini.ui.theme.MineShaft
import realaof.realhon.realha.cryptocurrencymini.ui.theme.RedOrange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@Composable
fun CoinTopList(
    modifier: Modifier = Modifier,
    isExpandable: Boolean,
    topPicks: List<LandingUiState.LandingUi.CoinUi>,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit = {}
) {
    val coin3TopPicks by remember { mutableStateOf(topPicks) }

    Column(
        horizontalAlignment = if (isExpandable) Alignment.CenterHorizontally else Alignment.Start,
        modifier = modifier,
    ) {
        TextInline(
            text = stringResource(id = R.string.coin_currency_coin_list_toppick_title_top),
            text2 = stringResource(id = R.string.coin_currency_coin_list_toppick_rank_title),
            inlineText = "${topPicks.size}",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MineShaft
            ),
            inLineTextStyle = MaterialTheme.typography.titleMedium.copy(
                color = RedOrange
            ),
            placeholder = Placeholder(
                width = 0.9.em,
                height = 21.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextBottom
            ),
            modifier = Modifier
                .padding(horizontal = dimen.dimen_16)
        )

        Spacer(modifier = Modifier.height(dimen.dimen_12))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier.align(alignment = Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen_8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                coin3TopPicks.forEach { coin ->
                    CoinTopItem(
                        coinUi = coin,
                        onClickedItem = onClickedItem
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinTopListPreview() {
    CoinTopList(
        topPicks = listOf(
            LandingUiState.LandingUi.CoinUi(
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
            ),
            LandingUiState.LandingUi.CoinUi(
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
            ),
            LandingUiState.LandingUi.CoinUi(
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
        ),
        isExpandable = false,
        onClickedItem = {}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showBackground = true,
    widthDp = 500
)
@Composable
private fun CoinCurrencyListPreview() {
    CoinCurrencyList(
        landingUi = LandingUiState.LandingUi(
            topPicks = listOf(
                LandingUiState.LandingUi.CoinUi(
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
                ),
                LandingUiState.LandingUi.CoinUi(
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
                ),
                LandingUiState.LandingUi.CoinUi(
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
            ).toMutableList(),
            coins = mutableListOf()
        ),
        pullToRefreshState = rememberPullRefreshState(refreshing = true, onRefresh = { /*TODO*/ }),
        onClickedItemToShared = {},
        onClickedItem = {},
        onLoadMore = {},
        windowSizeState = WindowSizeState(portrait = WindowSizeState.WindowAdaptive()),
        modifier = Modifier.fillMaxSize()
    )
}