package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.base.model.toBaseCommonError
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapper
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinDetailUseCase
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinListUseCase
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.SearchCoinUseCase
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val searchCoinUseCase: SearchCoinUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val coinCurrencyMapper: CoinCurrencyMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 0
        const val KEYWORDS_KEY = "KEYWORDS_KEY"
    }

    private val _landingUiState = MutableStateFlow(LandingUiState(loading = true))
    val landingUiState: StateFlow<LandingUiState> get() = _landingUiState.asStateFlow()

    val keyword = savedStateHandle.getStateFlow(key = KEYWORDS_KEY, initialValue = "")

    private val _coinDetailBottomSheetState = MutableStateFlow(CoinDetailUiState(loading = true))
    val coinDetailBottomSheetState: StateFlow<CoinDetailUiState>
        get() = _coinDetailBottomSheetState.asStateFlow()

    private val _offset = MutableStateFlow(FIRST_PAGE)

    private val _windowAdaptiveState =
        MutableStateFlow(WindowSizeState(portrait = WindowSizeState.default))
    val windowAdaptiveState: StateFlow<WindowSizeState> get() = _windowAdaptiveState.asStateFlow()

    fun getCoinList(
        limit: Int = PAGE_SIZE,
        isLoadMore: Boolean = false
    ) = viewModelScope.launch {
        getCoinListUseCase.execute(
            GetCoinListUseCase.Input(limit = limit, offset = _offset.value)
        )
            .onSuccess { response ->
                setLandingUiStateLogic(
                    response = response,
                    isLoadMore = isLoadMore,
                    isSearching = false
                )
            }
            .onFailure { error ->
                _landingUiState.set(LandingUiState(error = error.toBaseCommonError()))
            }
    }

    fun searchCoins(keyword: String, isLoadMore: Boolean = false, isSearching: Boolean? = false) =
        viewModelScope.launch {
            onSearchValueChange(keyword)

            when {
                keyword.isEmpty() -> initCoinList()

                else -> {
                    getSearchCoins(keyword, isLoadMore, isSearching = isSearching)
                }
            }
        }


    fun getSearchCoins(
        keyword: String,
        isLoadMore: Boolean = false,
        isSearching: Boolean? = false
    ) = viewModelScope.launch {
        searchCoinUseCase.execute(
            SearchCoinUseCase.Input(
                keyword = keyword,
                offset = _offset.value
            )
        )
            .onSuccess { response ->
                setLandingUiStateLogic(
                    response = response,
                    isLoadMore = isLoadMore,
                    isSearching = isSearching
                )
            }
            .onFailure { error ->
                _landingUiState.set(LandingUiState(error = error.toBaseCommonError()))
            }
    }

    fun getCoinDetail(uuid: String) {
        viewModelScope.launch {
            getCoinDetailUseCase.execute(GetCoinDetailUseCase.Input(uuid = uuid))
                .onSuccess { response ->
                    _coinDetailBottomSheetState.set(
                        CoinDetailUiState(success = coinCurrencyMapper.mapToCoinDetailUi(response))
                    )
                }
                .onFailure { error ->
                    _coinDetailBottomSheetState.set(CoinDetailUiState(error = error.toBaseCommonError()))
                }
        }
    }

    fun initCoinList() {
        resetOffset()
        getCoinList()
    }

    fun loadMoreCoins(keyword: String? = null, index: Int = 0) = viewModelScope.launch {
        if (index < PAGE_SIZE - 1) return@launch

        loadNextPage()

        if (keyword.isNullOrEmpty()) {
            getCoinList(isLoadMore = true)
        } else {
            searchCoins(keyword = keyword, isLoadMore = true)
        }
    }

    fun setAdaptiveWindowSizeSate(windowWidthSizeClass: WindowWidthSizeClass) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                _windowAdaptiveState.value = WindowSizeState(
                    portrait = WindowSizeState.WindowAdaptive(
                        adaptiveColumn = 1,
                        verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
                    )
                )
            }

            WindowWidthSizeClass.Medium -> {
                _windowAdaptiveState.value = WindowSizeState(
                    portrait = WindowSizeState.WindowAdaptive(
                        adaptiveColumn = 1,
                        horizontalArrangement = Arrangement.spacedBy(dimen.dimen_8),
                        verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
                    )
                )
            }

            WindowWidthSizeClass.Expanded -> {
                _windowAdaptiveState.value = WindowSizeState(
                    landscape = WindowSizeState.WindowAdaptive(
                        adaptiveColumn = 3,
                        horizontalArrangement = Arrangement.spacedBy(dimen.dimen_8),
                        verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
                    )
                )
            }
        }
    }

    private fun setLandingUiStateLogic(
        response: CoinCurrency,
        isLoadMore: Boolean = false,
        isSearching: Boolean? = false
    ) {
        when {
            isLoadMore.not() -> {
                if (response.data.coins.isEmpty()) {
                    _landingUiState.set(LandingUiState(empty = true))
                } else {
                    _landingUiState.set(
                        LandingUiState(
                            success = coinCurrencyMapper.mapToCoinLandingUi(response)
                                .copy(isSearching = isSearching)
                        )
                    )
                }
            }

            else -> appendCoinList(response)
        }
    }

    private fun appendCoinList(response: CoinCurrency) {
        _landingUiState.update { currentState ->
            val list = landingUiState.value.success?.coins
            val newList = mutableListOf<LandingUiState.LandingUi.CoinUi>()
            newList.addAll(list ?: mutableListOf())
            newList.addAll(coinCurrencyMapper.mapToCoinListUi(response.data.coins))

            LandingUiState(
                success = currentState.success?.copy(
                    coins = newList
                ) ?: LandingUiState.LandingUi()
            )
        }
    }

    private fun loadNextPage() {
        //Next Page offset == PageSize
        _offset.value += PAGE_SIZE
    }

    private fun resetOffset() {
        _offset.value = FIRST_PAGE
    }

    private fun MutableStateFlow<LandingUiState>.set(landingUi: LandingUiState) {
        this.value = landingUi
        landingUiState.value.updateLandingUi(this.value)
    }

    fun onSearchValueChange(keyword: String) {
        savedStateHandle[KEYWORDS_KEY] = keyword
    }

    private fun MutableStateFlow<CoinDetailUiState>.set(coinDetailUi: CoinDetailUiState) {
        this.value = coinDetailUi
    }
}