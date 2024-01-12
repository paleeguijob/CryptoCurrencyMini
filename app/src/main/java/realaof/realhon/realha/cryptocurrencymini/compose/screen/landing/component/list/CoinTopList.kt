package realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.item.CoinTopItem
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.item.TextInline
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel.LandingUiState
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
    val context = LocalContext.current
    val coin3TopPicks by remember { mutableStateOf(topPicks) }

    Column(
        modifier = modifier,
        horizontalAlignment = if (isExpandable) Alignment.CenterHorizontally else Alignment.Start
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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            coin3TopPicks.forEach { coin ->
                CoinTopItem(
                    coinUi = coin,
                    onClickedItem = onClickedItem
                )
                Spacer(modifier = Modifier.width(dimen.dimen_8))
            }
        }

        Spacer(modifier = Modifier.height(dimen.dimen_22))

        //title
        Text(
            text = stringResource(id = R.string.coin_currency_coin_list_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = dimen.dimen_8)
                .clearAndSetSemantics {
                    contentDescription =
                        context.getString(R.string.coin_currency_coin_list_title)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinTopListPreview() {
    CoinTopList(
        topPicks = emptyList(),
        isExpandable = false,
        onClickedItem = {}
    )
}