package realaof.realhon.realha.cryptocurrencymini.directions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.LandingScreen
import realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.LandingViewModel

@Composable
fun CoinCurrencyNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Landing.route,
    ) {
        composable(
            route = Landing.route
        ) {
//            LandingScreen(
//                viewModel = landingViewModel,
//                onClickedItem = { coin ->
//                    navController.navigateToDetail(uuid = coin.uuid)
//                }
//            )
//            LandingScreen()
        }

//        composable(
//            route = CoinDetail.routeWithArg,
//            arguments = CoinDetail.arguments,
//            deepLinks = CoinDetail.deepLinks
//        ) { navBackStackEntry ->
//            val uuid = navBackStackEntry.arguments?.getString(CoinDetail.uuidArg)
//
//            CoinDetailBottomSheet(
//                uuid = uuid.orEmpty(),
//                viewModel = coinDetailBottomSheetViewModel
//            )
//        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToDetail(uuid: String) {
    navigateSingleTopTo("${CoinDetail.route}/${uuid}")
}