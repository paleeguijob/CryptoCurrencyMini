package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.list

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
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
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CoinCurrencyList(
    modifier: Modifier = Modifier,
    windowSizeState: WindowSizeState,
    landingUi: LandingUiState.LandingUi,
    pullToRefreshState: PullRefreshState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
    onLoadMore: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    val keyboard = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    when {
        windowSizeState.portrait != null -> {
            CoinListContent(
                landingUi = landingUi,
                windowSizeState = windowSizeState,
                listState = listState,
                contentPadding = PaddingValues(
                    vertical = dimen.dimen_24,
                    horizontal = dimen.dimen_16
                ),
                pullToRefreshState = pullToRefreshState,
                onClickedItem = onClickedItem,
                onClickedItemToShared = onClickedItemToShared,
                onLoadMore = onLoadMore,
                modifier = modifier
            )
        }

        windowSizeState.landscape != null -> {
            CoinListContent(
                landingUi = landingUi,
                windowSizeState = windowSizeState,
                listState = listState,
                contentPadding = PaddingValues(
                    vertical = dimen.dimen_24,
                    horizontal = dimen.dimen_16
                ),
                pullToRefreshState = pullToRefreshState,
                onClickedItem = onClickedItem,
                onClickedItemToShared = onClickedItemToShared,
                onLoadMore = onLoadMore,
                modifier = modifier
            )
        }
    }

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
    windowSizeState: WindowSizeState,
    listState: LazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pullToRefreshState: PullRefreshState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
    onLoadMore: (Int) -> Unit = {},
) {
    val context = LocalContext.current
    val windowSize = windowSizeState.landscape ?: windowSizeState.portrait
    val isExpand = when {
        windowSizeState.landscape != null -> true
        windowSizeState.portrait != null -> false
        else -> false
    }

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
                    isExpandable = isExpand,
                    topPicks = landingUi.topPicks,
                    onClickedItem = onClickedItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimen.dimen_24)
                )
            }
        }

        item {
            //title
            Text(
                text = stringResource(id = R.string.coin_currency_coin_list_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = if (isExpand) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(bottom = dimen.dimen_24)
                    .padding(horizontal = dimen.dimen_16)
                    .clearAndSetSemantics {
                        contentDescription =
                            context.getString(R.string.coin_currency_coin_list_title)
                    }
            )
        }

        //Coin List
        item {
            CoinVerticalGridList(
                columns = SimpleGridCells.Fixed(windowSize?.adaptiveColumn ?: 1),
                landingUi = landingUi,
                horizontalArrangement = windowSize?.horizontalArrangement ?: Arrangement.Start,
                verticalArrangement = windowSize?.verticalArrangement ?: Arrangement.Top,
                onClickedItem = onClickedItem,
                onClickedItemToShared = onClickedItemToShared,
                modifier = Modifier
            )
        }
    }

    val scope = rememberCoroutineScope()
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
