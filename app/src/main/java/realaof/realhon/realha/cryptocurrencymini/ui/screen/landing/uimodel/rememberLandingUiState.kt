package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable

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