package realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import realaof.realhon.realha.cryptocurrencymini.ui.theme.MineShaft
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.util.NUMBER_WITH_COMMA_AND_DOLLAR_2DECIMAL
import realaof.realhon.realha.cryptocurrencymini.util.toMoneyFormat

@Composable
fun CoinDetailTextWithPrice(
    modifier: Modifier = Modifier,
    name: String,
    price: String
) {
    Row(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MineShaft,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(dimen.dimen_8))
        Text(
            text = price,
            style = MaterialTheme.typography.titleSmall.copy(
                color = MineShaft,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailTextWithPricePreview() {
    CoinDetailTextWithPrice(
        name = "PRICE",
        price = "5651422".toMoneyFormat(NUMBER_WITH_COMMA_AND_DOLLAR_2DECIMAL)
    )
}