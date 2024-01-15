package realaof.realhon.realha.cryptocurrencymini.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.LandingScreen
import realaof.realhon.realha.cryptocurrencymini.ui.theme.CryptoCurrencyMiniTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoCurrencyMiniTheme {
                val windowSizeClass = calculateWindowSizeClass(this)

                LandingScreen(windowSizeClass = windowSizeClass.widthSizeClass)
            }
        }
    }
}

@Preview
@Composable
private fun CoinCurrencyAppPreview() {
    CryptoCurrencyMiniTheme {
        LandingScreen(
            windowSizeClass = WindowWidthSizeClass.Compact
        )
    }
}