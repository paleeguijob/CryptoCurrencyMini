package realaof.realhon.realha.cryptocurrencymini.compose.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.base.compose.loading.BaseLoading
import realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.component.CoinDetailBottomSheetContent
import realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.orFalse
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@Composable
fun CoinDetailBottomSheet(
    modifier: Modifier = Modifier,
    coinDetailUiState: CoinDetailUiState,
    onDismissRequest: (Boolean) -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    when {
        coinDetailUiState.loading.orFalse() -> BaseLoading()

        coinDetailUiState.error != null -> {}

        coinDetailUiState.success != null -> {
            WrapContentCoinDetailBottomSheet(
                coinDetailUi = coinDetailUiState.success,
                modifier = modifier,
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WrapContentCoinDetailBottomSheet(
    modifier: Modifier = Modifier,
    coinDetailUi: CoinDetailUiState.CoinDetailUi,
    onDismissRequest: (Boolean) -> Unit = {}
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    ModalBottomSheet(
        windowInsets = WindowInsets.navigationBars,
        sheetState = bottomSheetState,
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest(false)
        }
    ) {
        CoinDetailBottomSheetContent(
            coinDetailUi = coinDetailUi,
            onClickedWebsiteButton = { webUrl ->
//                scope.launch {
//
//                    bottomSheetState.hide()
//                }.invokeOnCompletion {
//                    if (!bottomSheetState.isVisible) {
//                        onDismissRequest(false)
//                    }
//                }
                goToExternalBrowser(webUrl, context)
            }
        )
    }
}

private fun goToExternalBrowser(url: String, context: Context){
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