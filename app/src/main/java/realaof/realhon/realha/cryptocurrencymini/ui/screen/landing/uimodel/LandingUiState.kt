package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize
import realaof.realhon.realha.cryptocurrencymini.base.model.BaseCommonError
import realaof.realhon.realha.cryptocurrencymini.util.orFalse
import kotlin.math.pow

@Parcelize
class LandingUiState(
    val loading: Boolean? = false,
    val empty: Boolean? = false,
    val success: LandingUi? = null,
    val error: BaseCommonError? = null
) : Parcelable {
    var landingUiState by mutableStateOf(this)
        private set

    fun updateLandingUi(state: LandingUiState) {
        landingUiState = state
    }

    companion object {
        val Saver: Saver<LandingUiState, *> = listSaver(
            save = { listOf(it.loading, it.empty, it.error, it.success) },
            restore = {
                LandingUiState(
                    loading = it[0] as? Boolean ?: false,
                    empty = it[1] as? Boolean ?: false,
                    error = it[2] as? BaseCommonError?,
                    success = it[3] as? LandingUi?,
                )
            }
        )
    }

    @Parcelize
    data class LandingUi(
        val topPicks: MutableList<CoinUi> = mutableListOf(),
        val coins: MutableList<CoinUi> = mutableListOf(),
        val isSearching: Boolean? = false
    ) : Parcelable {

        @Parcelize
        data class CoinUi(
            val uuid: String = "",
            val iconUrl: String = "",
            val name: String = "",
            val price: String = "0.0",
            val symbol: CoinSymbol,
            val change: ChangeUi
        ) : Parcelable {

            @Parcelize
            data class CoinSymbol(
                val symbol: String,
                val color: String
            ) : Parcelable

            @Parcelize
            data class ChangeUi(
                @DrawableRes val arrowIcon: Int? = null,
                val change: String = "0.0",
                val color: String
            ) : Parcelable
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