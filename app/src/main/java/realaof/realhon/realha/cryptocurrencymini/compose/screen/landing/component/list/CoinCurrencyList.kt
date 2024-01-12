package realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.item.CoinInviteItem
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.item.CoinItem
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinCurrencyList(
    modifier: Modifier = Modifier,
    adaptiveWindowSizeState: WindowSizeState,
    landingUi: LandingUiState.LandingUi,
    pullToRefreshState: PullRefreshState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (text: String) -> Unit,
    onLoadMore: (Int) -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var cellAdaptive by rememberSaveable { mutableStateOf(1) }

    when {
        adaptiveWindowSizeState.portrait != null -> {
            InfiniteLazyColumnScrollList(
                itemCount = landingUi.coins.size,
                contentPadding = PaddingValues(
                    horizontal = dimen.dimen_16,
                    vertical = dimen.dimen_12
                ),
                verticalArrangement = Arrangement.spacedBy(dimen.dimen_16),
                listState = listState,
                headerContent = {
                    //top picks
                    item {
                        if (landingUi.isShowTopPicks) {
                            CoinTopList(
                                isExpandable = false,
                                topPicks = landingUi.topPicks,
                                onClickedItem = onClickedItem,
                                modifier = Modifier.height(dimen.dimen_242)
                            )
                        }
                    }
                },
                content = { index ->
                    if (landingUi.isContainPosition(index)) {
                        CoinInviteItem(
                            onClickedItemToShared = onClickedItemToShared
                        )
                    } else {
                        CoinItem(
                            coinUi = landingUi.coins[index],
                            onClickedItem = onClickedItem
                        )
                    }
                },
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullToRefreshState)
                    .background(Color.White),
                onLoadMore = onLoadMore,
            )
        }

        adaptiveWindowSizeState.landscape != null -> {
            InfiniteLazyVerticalGrid(
                itemCount = landingUi.coins.size,
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullToRefreshState)
                    .background(Color.White),
                contentPadding = PaddingValues(
                    horizontal = dimen.dimen_16,
                    vertical = dimen.dimen_12
                ),
                verticalArrangement = Arrangement.spacedBy(dimen.dimen_24),
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen_16),
                headerContent = {
                    //top picks
                    if (landingUi.isShowTopPicks) {
                        CoinTopList(
                            isExpandable = true,
                            topPicks = landingUi.topPicks,
                            onClickedItem = onClickedItem,
                            modifier = Modifier.height(dimen.dimen_242)
                        )
                    }
                },
                onLoadMore = onLoadMore
            ) { index ->
                if (landingUi.isContainPosition(index)) {
                    CoinInviteItem(
                        onClickedItemToShared = onClickedItemToShared
                    )
                } else {
                    CoinItem(
                        coinUi = landingUi.coins[index],
                        onClickedItem = onClickedItem
                    )
                }
            }
        }
    }
}

@Composable
fun InfiniteLazyColumnScrollList(
    modifier: Modifier = Modifier,
    itemCount: Int,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    listState: LazyListState,
    onLoadMore: (Int) -> Unit,
    headerContent: LazyListScope.() -> Unit = {},
    content: @Composable (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        state = listState
    ) {
        headerContent()

        items(count = itemCount) { index ->
            content(index)

            if (index == itemCount - 1) {

                Log.d("AOFAOF", "$index")

                onLoadMore(index)
            }
        }
    }
}

@Composable
fun InfiniteLazyVerticalGrid(
    itemCount: Int,
    columns: GridCells,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    onLoadMore: (Int) -> Unit,
    headerContent: @Composable () -> Unit,
    content: @Composable (Int) -> Unit,
) {
    LazyColumn {
        item {
            headerContent()
        }

        item {
            LazyVerticalGrid(
                modifier = modifier.height(dimen.dimen_500),
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement,
                columns = columns,
                flingBehavior = flingBehavior,
                userScrollEnabled = userScrollEnabled,
                state = state
            ) {
                items(count = itemCount) { index ->
                    content(index)

                    if (index == itemCount - 1) {
                        onLoadMore(index)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun CoinCurrencyListPreview() {
    CoinCurrencyList(
        landingUi = LandingUiState.LandingUi(
            topPicks = mutableListOf(),
            coins = mutableListOf()
        ),
        pullToRefreshState = rememberPullRefreshState(refreshing = true, onRefresh = { /*TODO*/ }),
        onClickedItemToShared = {},
        onClickedItem = {},
        onLoadMore = {},
        adaptiveWindowSizeState = WindowSizeState(),
//        windowWidthSize = WindowWidthSizeClass.Compact
    )
}
