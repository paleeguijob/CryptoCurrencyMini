package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import realaof.realhon.realha.cryptocurrencymini.base.model.toBaseCommonError
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapper
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinDetailUseCase
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import javax.inject.Inject

@HiltViewModel
class CoinDetailBottomSheetViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val coinCurrencyMapper: CoinCurrencyMapper,
) : ViewModel() {

    private val _coinDetailBottomSheetState = MutableStateFlow(CoinDetailUiState(loading = true))
    val coinDetailBottomSheetState: StateFlow<CoinDetailUiState>
        get() = _coinDetailBottomSheetState.asStateFlow()

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

    fun clearCoinDetailStateValue() {
        _coinDetailBottomSheetState.set(CoinDetailUiState())
    }

    private fun MutableStateFlow<CoinDetailUiState>.set(coinDetailUi: CoinDetailUiState) {
        this.value = coinDetailUi
    }
}