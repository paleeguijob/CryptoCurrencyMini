package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.base.compose.error.BaseErrorScreen
import realaof.realhon.realha.cryptocurrencymini.base.compose.loading.BaseLoading
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.component.CoinDetailBottomSheetContent
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.orFalse
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailBottomSheet(
    modifier: Modifier = Modifier,
    uuid: String,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    viewModel: CoinDetailBottomSheetViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.getCoinDetail(uuid)
    }

    val coinDetailUiState by viewModel.coinDetailBottomSheetState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()


    WrapContentCoinDetailBottomSheet(
        coinDetailUiState = coinDetailUiState,
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                viewModel.clearCoinDetailStateValue()
                onDismissRequest()
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WrapContentCoinDetailBottomSheet(
    modifier: Modifier = Modifier,
    coinDetailUiState: CoinDetailUiState,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
    when {
        coinDetailUiState.loading.orFalse() -> BaseLoading(modifier = modifier.fillMaxSize())

        coinDetailUiState.error != null -> BaseErrorScreen(
            title = stringResource(id = R.string.coin_currency_common_search_error_title),
            message = stringResource(id = R.string.coin_currency_common_search_error_message),
            modifier = Modifier
        )

        coinDetailUiState.success != null -> {
            val context = LocalContext.current

            ModalBottomSheet(
                windowInsets = WindowInsets.navigationBars,
                sheetState = sheetState,
                onDismissRequest = onDismissRequest,
                modifier = modifier,
            ) {
                CoinDetailBottomSheetContent(
                    coinDetailUi = coinDetailUiState.success,
                    onClickedWebsiteButton = { webUrl ->
                        goToExternalBrowser(webUrl, context)
                    },
                    modifier = modifier
                )
            }
        }
    }
}

private fun goToExternalBrowser(url: String, context: Context) {
    Intent(
        Intent.ACTION_VIEW, Uri.parse(url)
    ).apply {
        context.startActivity(this)
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinBottomSheetDetailPreview() {
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