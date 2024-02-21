package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

data class WindowSizeState(
    val windowAdaptive: WindowAdaptive? = null
) {

    data class WindowAdaptive(
        val adaptiveColumn: Int = 3,
        val horizontalArrangement: CoinHorizontalAlignment = CoinHorizontalAlignment(),
        val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        val coinTextAlign: CoinTextAlignment = CoinTextAlignment(),
        val paddingValue: PaddingValues = PaddingValues(
            vertical = dimen.dimen_24, horizontal = dimen.dimen_16
        )
    )

    data class CoinHorizontalAlignment(
        val topRankHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
        val lazyHorizontalArrangement: Arrangement.Horizontal = Arrangement.Start
    )

    data class CoinTextAlignment(
        val textAlign: TextAlign = TextAlign.Start
    )

    companion object {
        val default get() = WindowAdaptive()
    }
}

