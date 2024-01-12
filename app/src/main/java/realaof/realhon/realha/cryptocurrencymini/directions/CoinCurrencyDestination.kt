package realaof.realhon.realha.cryptocurrencymini.directions

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface CoinCurrencyDestination {
    val title: String
    val route: String
}

object Landing : CoinCurrencyDestination {
    override val title: String = "Landing"
    override val route: String = "landing"
}

object CoinDetail : CoinCurrencyDestination {
    override val title: String = "CoinDetailBottomSheet"
    override val route: String = "detail_bottom_sheet"

    val uuidArg = "uuid_arg"
    val routeWithArg = "$route/{$uuidArg}"
    val arguments = listOf(navArgument(uuidArg) { type = NavType.StringType })
    val deepLinks = listOf(navDeepLink { uriPattern = "coincurrency://$route/{${uuidArg}}" })
}