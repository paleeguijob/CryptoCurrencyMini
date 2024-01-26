package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel

import androidx.compose.foundation.layout.Arrangement

data class WindowSizeState(
    val portrait: WindowAdaptive? = null,
    val landscape: WindowAdaptive? = null
) {

    data class WindowAdaptive(
        val adaptiveColumn: Int = 3,
        val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    )

    companion object {
        val default get() = WindowAdaptive()
    }
}

