package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.base.compose.error.BaseErrorScreen
import realaof.realhon.realha.cryptocurrencymini.base.compose.loading.BaseLoading
import realaof.realhon.realha.cryptocurrencymini.base.compose.search.SearchBar
import realaof.realhon.realha.cryptocurrencymini.base.compose.search.rememberEditableSearchInputState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.CoinDetailBottomSheet
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.component.list.CoinCurrencyList
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember.CoinListLoadMoreState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember.rememberLandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SearchBg
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.util.orFalse

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowWidthSizeClass,
    viewModel: LandingViewModel = hiltViewModel(),
) {
    //set Adaptive Window Size
    LaunchedEffect(key1 = windowSizeClass) {
        viewModel.setAdaptiveWindowSizeSate(windowSizeClass)
    }

    //Call Api first time prevent state change ex. rotate portrait to landscape
    rememberSaveable(saver = LandingUiState.Saver) {
        viewModel.getCoinList()
        LandingUiState(loading = true)
    }

    val scope = rememberCoroutineScope()

    //init value
    val landingUi by viewModel.landingUiState.collectAsStateWithLifecycle()
    val query by viewModel.keyword.collectAsStateWithLifecycle()
    val coinDetailUi by viewModel.coinDetailBottomSheetState.collectAsStateWithLifecycle()
    val adaptiveWindowSizeState by viewModel.windowAdaptiveState.collectAsStateWithLifecycle()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    //Search state
    val searchInputState = rememberEditableSearchInputState(
        hint = stringResource(id = R.string.coin_currency_common_search),
        keyword = query
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    //Loading more state
    val coinLoading by viewModel.coinListLoadMoreState.collectAsStateWithLifecycle()

    var refreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            scope.launch {
                //delay indicator pull refresh progress
                searchInputState.clearText()
                keyboardController?.hide()
                refreshing = true
                viewModel.initCoinList()
                delay(1000)
                refreshing = landingUi.loading.orFalse()
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //Order Component
        Column {
            SearchBar(
                state = searchInputState,
                onValueChange = { newText ->
                    scope.launch {
                        viewModel.searchCoins(newText, isSearching = true)
                    }
                },
                onSearchAction = { newText ->
                    scope.launch {
                        viewModel.searchCoins(newText, isSearching = true)
                        keyboardController?.hide()
                    }
                },
                onClickedClearText = {
                    keyboardController?.hide()
                    viewModel.onSearchValueChange("")
                    viewModel.initCoinList()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimen.dimen_16)
            )

            HorizontalDivider(color = SearchBg)

            WrapLandingContent(
                adaptiveWindowSizeState = adaptiveWindowSizeState,
                landingState = landingUi,
                pullToRefreshState = pullToRefreshState,
                coinListLoadMoreState = coinLoading,
                onClickedItem = { coin ->
                    scope.launch {
                        showBottomSheet = true
                        viewModel.getCoinDetail(coin.uuid)
                    }
                },
                onLoadMore = { index ->
                    scope.launch {
                        delay(500)
                        viewModel.loadMoreCoins(keyword = searchInputState.text, index = index)
                    }
                }
            )
        }

        //Pull to Refresh Indicator
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullToRefreshState,
            backgroundColor = Orange,
            scale = true,
            modifier = Modifier.align(alignment = Alignment.TopCenter)
        )

        if (showBottomSheet) {
            CoinDetailBottomSheet(
                coinDetailUiState = coinDetailUi,
                onDismissRequest = { isShowBottomSheet ->
                    scope.launch {
                        showBottomSheet = isShowBottomSheet
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WrapLandingContent(
    adaptiveWindowSizeState: WindowSizeState,
    landingState: LandingUiState,
    coinListLoadMoreState: CoinListLoadMoreState,
    pullToRefreshState: PullRefreshState,
    onClickedItem: (coin: LandingUiState.LandingUi.CoinUi) -> Unit,
    onLoadMore: (Int) -> Unit
) {
    val context = LocalContext.current
    val stateUi = rememberLandingUiState(landingUiState = landingState)

    when {
        stateUi.loading.orFalse() -> BaseLoading(modifier = Modifier.fillMaxSize())

        stateUi.empty.orFalse() -> {
            BaseErrorScreen(
                title = stringResource(id = R.string.coin_currency_common_search_error_title),
                message = stringResource(id = R.string.coin_currency_common_search_error_message)
            )
        }

        stateUi.error != null -> {
            BaseErrorScreen(
                icon = Icons.Filled.ErrorOutline,
                title = stringResource(id = R.string.coin_currency_common_search_error_title),
                message = stateUi.error.message.orEmpty()
            )
        }

        stateUi.success != null -> {
            LandingContent(
                adaptiveWindowSizeState = adaptiveWindowSizeState,
                landingUi = stateUi.success,
                pullToRefreshState = pullToRefreshState,
                coinListLoadMoreState = coinListLoadMoreState,
                onClickedItem = onClickedItem,
                onClickedItemToShared = { earnCoinShareText ->
                    shareContent(context = context, paintText = earnCoinShareText)
                },
                onLoadMore = onLoadMore
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LandingContent(
    modifier: Modifier = Modifier,
    adaptiveWindowSizeState: WindowSizeState,
    landingUi: LandingUiState.LandingUi,
    pullToRefreshState: PullRefreshState,
    coinListLoadMoreState: CoinListLoadMoreState,
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (String) -> Unit,
    onLoadMore: (Int) -> Unit,
) {
    CoinCurrencyList(
        windowSizeState = adaptiveWindowSizeState,
        landingUi = landingUi,
        pullToRefreshState = pullToRefreshState,
        coinListLoadMoreState = coinListLoadMoreState,
        onClickedItem = onClickedItem,
        onClickedItemToShared = onClickedItemToShared,
        onLoadMore = onLoadMore,
        modifier = modifier
    )
}

private fun shareContent(context: Context, paintText: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, paintText)
        type = "text/plain"
    }

    context.startActivity(
        Intent.createChooser(
            sendIntent,
            context.getString(R.string.coin_currency_coin_share_earn_coin_title)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun LandingScreenPreview() {
    LandingScreen(
        windowSizeClass = WindowWidthSizeClass.Compact
    )
}

