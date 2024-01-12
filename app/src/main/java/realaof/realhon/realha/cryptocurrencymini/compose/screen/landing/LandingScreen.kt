package realaof.realhon.realha.cryptocurrencymini.compose.screen.landing

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.base.compose.error.BaseErrorScreen
import realaof.realhon.realha.cryptocurrencymini.base.compose.loading.BaseLoading
import realaof.realhon.realha.cryptocurrencymini.base.compose.search.SearchBar
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.list.CoinCurrencyList
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.LandingUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import realaof.realhon.realha.cryptocurrencymini.base.compose.search.rememberEditableSearchInputState
import realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.CoinDetailBottomSheet
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SearchBg
import realaof.realhon.realha.cryptocurrencymini.util.orFalse

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowWidthSizeClass,
    viewModel: LandingViewModel = viewModel(),
) {
    //set Adaptive Window Size
    LaunchedEffect(key1 = windowSizeClass) {
        viewModel.setAdaptiveWindowSizeSate(windowSizeClass)
    }

    val scope = rememberCoroutineScope()

    val landingUi by viewModel.landingUiState.collectAsStateWithLifecycle()
    val coinDetailUi by viewModel.coinDetailBottomSheetState.collectAsStateWithLifecycle()
    val adaptiveWindowSizeState by viewModel.windowAdaptiveState.collectAsStateWithLifecycle()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val searchInputState = rememberEditableSearchInputState(
        hint = stringResource(id = R.string.coin_currency_common_search),
    )
    val keyboardController = LocalSoftwareKeyboardController.current

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimen.dimen_16),
                state = searchInputState,
                onValueChange = { newText ->
                    viewModel.searchCoins(newText, isSearching = true)
                },
                onSearchAction = { newText ->
                    viewModel.searchCoins(newText, isSearching = true)
                    keyboardController?.hide()
                },
                onClickedClearText = {
                    keyboardController?.hide()
                    viewModel.initCoinList()
                }
            )

            Divider(color = SearchBg)

            WrapLandingContent(
                adaptiveWindowSizeState = adaptiveWindowSizeState,
                landingState = landingUi,
                pullToRefreshState = pullToRefreshState,
                onClickedItem = { coin ->
                    scope.launch {
                        showBottomSheet = true
                        viewModel.getCoinDetail(coin.uuid)
                    }
                },
                onLoadMore = { index ->
                    viewModel.loadMoreCoins(index = index)
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
    pullToRefreshState: PullRefreshState,
    onClickedItem: (coin: LandingUiState.LandingUi.CoinUi) -> Unit,
    onLoadMore: (Int) -> Unit
) {
    val context = LocalContext.current

    //State Ui
    when {
        landingState.loading.orFalse() -> BaseLoading(modifier = Modifier.fillMaxSize())

        landingState.empty.orFalse() -> {
            BaseErrorScreen(
                title = stringResource(id = R.string.coin_currency_common_search_error_title),
                message = stringResource(id = R.string.coin_currency_common_search_error_message)
            )
        }

        landingState.error != null -> {
            BaseErrorScreen(
                icon = Icons.Filled.ErrorOutline,
                title = stringResource(id = R.string.coin_currency_common_search_error_title),
                message = landingState.error.message.orEmpty()
            )
        }

        landingState.success != null -> {
            LandingContent(
                adaptiveWindowSizeState = adaptiveWindowSizeState,
                landingUi = landingState.success,
                pullToRefreshState = pullToRefreshState,
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
    onClickedItem: (LandingUiState.LandingUi.CoinUi) -> Unit,
    onClickedItemToShared: (String) -> Unit,
    onLoadMore: (Int) -> Unit,
) {
    CoinCurrencyList(
        adaptiveWindowSizeState = adaptiveWindowSizeState,
        landingUi = landingUi,
        pullToRefreshState = pullToRefreshState,
        modifier = modifier,
        onClickedItem = onClickedItem,
        onClickedItemToShared = onClickedItemToShared,
        onLoadMore = onLoadMore
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

