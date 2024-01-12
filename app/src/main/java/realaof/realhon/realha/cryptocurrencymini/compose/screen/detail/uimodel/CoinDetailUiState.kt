package realaof.realhon.realha.cryptocurrencymini.compose.screen.detail.uimodel

import androidx.compose.ui.graphics.Color
import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError

data class CoinDetailUiState(
    val loading: Boolean? = false,
    val success: CoinDetailUi? = null,
    val error: BaseCommonError? = null
) {
    data class CoinDetailUi(
        val header: HeaderUi,
        val detail: String = "",
        val coinWebUrl: String = ""
    ) {
        data class HeaderUi(
            val coinUrl: String = "",
            val name: String = "",
            val symbol: CoinSymbol,
            val price: String = "0.0",
            val marketCap: String = "0.0"
        ) {
            data class CoinSymbol(
                val symbol: String,
                val color: Color
            )
        }
    }
}