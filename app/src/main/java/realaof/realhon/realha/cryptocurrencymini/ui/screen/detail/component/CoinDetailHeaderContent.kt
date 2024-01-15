package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyCurrency
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@Composable
fun CoinDetailHeaderContent(
    modifier: Modifier = Modifier,
    headerUi: CoinDetailUiState.CoinDetailUi.HeaderUi,
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = headerUi.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    color = headerUi.symbol.color,
                    fontWeight = FontWeight.W700
                ),
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = "Coin Detail Header, Name: $headerUi.name"
                }
            )
            Text(
                text = "(${headerUi.symbol.symbol})",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = "Coin Detail Header, Symbol: ${headerUi.symbol.symbol}"
                }
            )
        }
        CoinDetailTextWithPrice(
            name = stringResource(id = R.string.coin_currency_detail_coin_header_price),
            price = headerUi.price
        )
        CoinDetailTextWithPrice(
            name = stringResource(id = R.string.coin_currency_detail_coin_header_market_cap),
            price = headerUi.marketCap
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailHeaderContentPreview() {
    CoinDetailHeaderContent(
        headerUi = CoinDetailUiState.CoinDetailUi.HeaderUi(
            name = "BitCoin",
            symbol = CoinDetailUiState.CoinDetailUi.HeaderUi.CoinSymbol(
                symbol = "BTC",
                color = Orange
            ),
            price = "45973.474499812466".toMoneyFormat(NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL),
            marketCap = "900819752410".toMoneyCurrency()
        )
    )
}