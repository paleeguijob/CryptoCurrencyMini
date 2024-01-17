package realaof.realhon.realha.cryptocurrencymini.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.LandingScreen
import realaof.realhon.realha.cryptocurrencymini.ui.theme.CryptoCurrencyMiniTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d("AOFAOF","save: "+savedInstanceState)


        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

//            val viewModel: LandingViewModel = hiltViewModel()
//            if (savedInstanceState == null) {
//                viewModel.getCoinList()
//            }

            CoinCurrencyApp(
                modifier = Modifier,
                windowSizeClass = windowSizeClass,
//                viewModel = viewModel
            )
        }
    }
}

@Composable
fun CoinCurrencyApp(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
//    viewModel: LandingViewModel
) {
    CryptoCurrencyMiniTheme {
        LandingScreen(
            modifier = modifier,
            windowSizeClass = windowSizeClass.widthSizeClass,
//            viewModel = viewModel
        )
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