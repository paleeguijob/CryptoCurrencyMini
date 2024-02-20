package realaof.realhon.realha.cryptocurrencymini.directions

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.LandingScreen

@Composable
fun CoinCurrencyNavHost(
    navController: NavHostController,
    windowSizeClass: WindowWidthSizeClass,
) {
    NavHost(
        navController = navController,
        startDestination = Landing.route,
    ) {
        composable(
            route = Landing.route
        ) {
            LandingScreen(
                windowSizeClass = windowSizeClass
            )
        }
    }
}

//fun NavHostController.navigateSingleTopTo(route: String) =
//    this.navigate(route) {
//        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }
//
//fun NavHostController.navigateToDetail(uuid: String) {
//    navigateSingleTopTo("${CoinDetail.route}/${uuid}")
//}