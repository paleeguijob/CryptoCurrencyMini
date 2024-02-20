package realaof.realhon.realha.cryptocurrencymini.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import realaof.realhon.realha.cryptocurrencymini.directions.CoinCurrencyNavHost
import realaof.realhon.realha.cryptocurrencymini.ui.theme.CryptoCurrencyMiniTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val navController = rememberNavController()

            CoinCurrencyApp(
                navHostController = navController,
                windowWidthSizeClass = windowSizeClass.widthSizeClass,
            )
        }
    }
}

@Composable
fun CoinCurrencyApp(
    navHostController: NavHostController,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    CryptoCurrencyMiniTheme {
        CoinCurrencyNavHost(
            navController = navHostController,
            windowSizeClass = windowWidthSizeClass
        )
    }
}

@Preview
@Composable
private fun CoinCurrencyAppPreview() {
    CryptoCurrencyMiniTheme {
        CoinCurrencyNavHost(
            navController = rememberNavController(),
            windowSizeClass = WindowWidthSizeClass.Medium
        )
    }
}