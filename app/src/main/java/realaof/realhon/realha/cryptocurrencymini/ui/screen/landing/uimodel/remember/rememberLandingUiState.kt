package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState

@Composable
fun rememberLandingUiState(
    landingUiState: LandingUiState
) = rememberSaveable(landingUiState, saver = LandingUiState.Saver) {
    LandingUiState(
        loading = landingUiState.loading,
        success = landingUiState.success,
        empty = landingUiState.empty,
        error = landingUiState.error
    )
}