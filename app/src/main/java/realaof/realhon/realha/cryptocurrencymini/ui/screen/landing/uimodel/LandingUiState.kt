package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError
import realaof.realhon.realha.cryptocurrencymini.util.orFalse
import kotlin.math.pow

data class LandingUiState(
    val loading: Boolean? = false,
    val empty: Boolean? = false,
    val success: LandingUi? = null,
    val error: BaseCommonError? = null
) {
    data class LandingUi(
        val topPicks: MutableList<CoinUi> = mutableListOf(),
        val coins: MutableList<CoinUi> = mutableListOf(),
        val isSearching : Boolean? = false
    ) {
        data class CoinUi(
            val uuid: String = "",
            val iconUrl: String = "",
            val name: String = "",
            val price: String = "0.0",
            val symbol: CoinSymbol,
            val change: ChangeUi
        ) {
            data class CoinSymbol(
                val symbol: String,
                val color: Color
            )

            data class ChangeUi(
                @DrawableRes val arrowIcon: Int? = null,
                val change: String = "0.0",
                val color: Color
            )
        }

        val isShowTopPicks: Boolean get() = topPicks.isNotEmpty() && !isSearching.orFalse()

        fun isContainPosition(index: Int): Boolean {
            return positions.any { it == index }
        }

        private val positions: MutableList<Int>
            get() {
                val positions: MutableList<Int> = mutableListOf()
                coins.forEachIndexed { indexItem, _ ->
                    positions.add((5 * 2.0.pow(indexItem)).toInt())
                }
                return positions.toMutableList()
            }
    }
}