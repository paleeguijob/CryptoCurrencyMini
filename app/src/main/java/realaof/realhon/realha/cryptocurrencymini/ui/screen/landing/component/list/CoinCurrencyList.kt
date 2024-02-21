package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item.CoinInviteItem
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.item.CoinItem
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember.CoinListLoadMoreState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember.rememberCoinLisLoadMoreState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinCurrencyList(
    modifier: Modifier = Modifier,
    windowAdaptiveSizeState: WindowSizeState,
    landingUi: LandingUiState.LandingUi,
    pullToRefreshState: PullRefreshState,
    coinListLoadMoreState: CoinListLoadMoreState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
    onLoadMore: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val keyboard = LocalSoftwareKeyboardController.current

    CoinListContent(
        landingUi = landingUi,
        windowAdaptive = windowAdaptiveSizeState.windowAdaptive ?: WindowSizeState.WindowAdaptive(),
        listState = listState,
        coinListLoadMoreState = coinListLoadMoreState,
        pullToRefreshState = pullToRefreshState,
        contentPadding = windowAdaptiveSizeState.windowAdaptive?.paddingValue ?: PaddingValues(),
        onClickedItem = onClickedItem,
        onClickedItemToShared = onClickedItemToShared,
        onLoadMore = onLoadMore,
        modifier = modifier
    )

    LaunchedEffect(key1 = listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect {
                scope.launch {
                    if (it) {
                        keyboard?.hide()
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinListContent(
    modifier: Modifier = Modifier,
    landingUi: LandingUiState.LandingUi,
    windowAdaptive: WindowSizeState.WindowAdaptive,
    listState: LazyListState,
    coinListLoadMoreState: CoinListLoadMoreState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pullToRefreshState: PullRefreshState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
    onLoadMore: (Int) -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier
            .pullRefresh(pullToRefreshState)
    ) {
        //top picks
        item {
            if (landingUi.isShowTopPicks) {
                CoinTopList(
                    horizontalAlignment = windowAdaptive.horizontalArrangement.topRankHorizontalAlignment,
                    topPicks = landingUi.topPicks,
                    onClickedItem = onClickedItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimen.dimen_24)
                )
            }
        }

        //Coin List
        item {
            // Title
            Text(
                text = stringResource(id = R.string.coin_currency_coin_list_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = windowAdaptive.coinTextAlign.textAlign,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(bottom = dimen.dimen_24)
                    .padding(horizontal = dimen.dimen_16)
                    .clearAndSetSemantics {
                        contentDescription =
                            context.getString(R.string.coin_currency_coin_list_title)
                    }
            )

            CoinVerticalGridList(
                columns = SimpleGridCells.Fixed(windowAdaptive.adaptiveColumn),
                landingUi = landingUi,
                horizontalArrangement = windowAdaptive.horizontalArrangement.lazyHorizontalArrangement,
                verticalArrangement = windowAdaptive.verticalArrangement,
                onClickedItem = onClickedItem,
                onClickedItemToShared = onClickedItemToShared,
                modifier = Modifier
            )
        }

        scope.launch {
            if (coinListLoadMoreState.isLoadMore) {

                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimen.dimen_16)
                    ) {
                        CircularProgressIndicator(color = Orange)
                    }
                }
            }
        }
    }

    //send event load more
    LaunchedEffect(key1 = listState, key2 = landingUi) {
        snapshotFlow { listState.canScrollForward }
            .collect {
                scope.launch {
                    if (it.not()) {
                        onLoadMore(landingUi.coins.size - 1)
                    }
                }
            }
    }
}

@Composable
fun CoinVerticalGridList(
    modifier: Modifier = Modifier,
    columns: SimpleGridCells,
    landingUi: LandingUiState.LandingUi,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
) {
    VerticalGrid(
        modifier = modifier,
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        landingUi.coins.forEachIndexed { index, coin ->
            if (landingUi.isContainPosition(index)) {
                CoinInviteItem(
                    onClickedItemToShared = onClickedItemToShared
                )
            } else {
                CoinItem(
                    coinUi = coin,
                    onClickedItem = onClickedItem
                )
            }
        }
    }
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
                        color = "#f7931A"
                    ),
                    change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                        change = "1.8",
                        color = "#000000"
                    )
                ),
                LandingUiState.LandingUi.CoinUi(
                    iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                    name = "Bitcoin",
                    price = "",
                    symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                        symbol = "BTC",
                        color = "#f7931A"
                    ),
                    change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                        change = "1.8",
                        color = "#000000"
                    )
                ),
                LandingUiState.LandingUi.CoinUi(
                    iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                    name = "Bitcoin",
                    price = "",
                    symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                        symbol = "BTC",
                        color = "#f7931A"
                    ),
                    change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                        change = "1.8",
                        color = "#000000"
                    )
                )
            ).toMutableList(),
            coins = mutableListOf(
                LandingUiState.LandingUi.CoinUi(
                    uuid = "",
                    iconUrl = "",
                    name = "",
                    price = "",
                    symbol = LandingUiState.LandingUi.CoinUi.CoinSymbol(
                        "BTC",
                        "#000000"
                    ),
                    change = LandingUiState.LandingUi.CoinUi.ChangeUi(
                        arrowIcon = R.drawable.ic_arrow_up,
                        change = "0.1",
                        color = "#000000"
                    )
                )
            )
        ),
        pullToRefreshState = rememberPullRefreshState(refreshing = true, onRefresh = { /*TODO*/ }),
        coinListLoadMoreState = rememberCoinLisLoadMoreState(loadingMore = false) {},
        onClickedItemToShared = {},
        onClickedItem = {},
        onLoadMore = {},
        windowAdaptiveSizeState = WindowSizeState(windowAdaptive = WindowSizeState.WindowAdaptive()),
        modifier = Modifier.fillMaxSize()
    )
}
