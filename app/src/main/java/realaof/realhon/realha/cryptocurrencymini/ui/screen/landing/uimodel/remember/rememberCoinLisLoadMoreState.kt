package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberCoinLisLoadMoreState(
    loadingMore: Boolean,
    onLoadMore: (Int) -> Unit
) = rememberSaveable(loadingMore, onLoadMore, saver = CoinListLoadMoreState.Saver) {
    CoinListLoadMoreState(
        loadingMore = loadingMore,
        onLoadMore = onLoadMore
    )
}

data class CoinListLoadMoreState(
    private val loadingMore: Boolean = false,
    private val onLoadMore: (Int) -> Unit = {}
) {
    var isLoadMore by mutableStateOf(loadingMore)
        private set

    companion object {
        val Saver: Saver<CoinListLoadMoreState, *> = listSaver(
            save = { listOf(it.isLoadMore) },
            restore = {
                CoinListLoadMoreState(
                    loadingMore = it[0]
                )
            }
        )
    }
}