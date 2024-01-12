package realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency

data class CoinCurrency(
    val data: Data,
    val status: String
)

data class Data(
    val coins: List<Coin> = emptyList(),
    val stats: Stats
)

data class Stats(
    val total: Int,
    val total24hVolume: String,
    val totalCoins: Int,
    val totalExchanges: Int,
    val totalMarketCap: String,
    val totalMarkets: Int
)

data class Coin(
    val uuid: String? = "",
    val symbol: String? = "",
    val name: String? = "",
    val color: String? = "",
    val iconUrl: String? = "",
    val marketCap: String? = "",
    val price: String? = "",
    val btcPrice: String? = "",
    val listedAt: Int? = 0,
    val tier: Int? = 0,
    val change: String? = "",
    val rank: Int? = 0,
    val sparkline: List<String>? = emptyList(),
    val coinrankingUrl: String? = "",
    val `24hVolume`: String? = ""
)