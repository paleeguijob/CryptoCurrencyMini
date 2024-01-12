package realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.uimodel

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

data class WindowSizeState(
    val portrait: WindowAdaptive? = null,
    val landscape: WindowAdaptive? = null
) {

    data class WindowAdaptive(
        val adaptiveColum: Int = 3,
        val paddingValues: PaddingValues = PaddingValues(0.dp)
    )

    companion object {
        val default get() = WindowAdaptive()
    }
}

