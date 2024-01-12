package realaof.realhon.realha.cryptocurrencymini.data.model.coindetail

data class CoinDetail(
    val `data`: Data,
    val status: String
)

data class Data(
    val coin: Coin? = Coin()
)

data class Coin(
    val `24hVolume`: String? = "",
    val allTimeHigh: AllTimeHigh? = AllTimeHigh(),
    val btcPrice: String? = "",
    val change: String? = "",
    val coinrankingUrl: String? = "",
    val color: String? = "",
    val description: String? = "",
    val fullyDilutedMarketCap: String? = "",
    val hasContent: Boolean? = false,
    val iconUrl: String? = "",
    val links: List<Link>? = emptyList(),
    val listedAt: Int? = 0,
    val lowVolume: Boolean? = false,
    val marketCap: String? = "",
    val name: String? = "",
    val notices: List<Notice> = emptyList(),
    val numberOfExchanges: Int? = 0,
    val numberOfMarkets: Int? = 0,
    val price: String? = "",
    val priceAt: Int? = 0,
    val rank: Int? = 0,
    val sparkline: List<String>? = emptyList(),
    val supply: Supply? = Supply(),
    val symbol: String? = "",
    val tags: List<String>? = emptyList(),
    val tier: Int? = 0,
    val uuid: String? = "",
    val websiteUrl: String? = ""
)

data class Link(
    val name: String? = "",
    val type: String? = "",
    val url: String? = ""
)

data class Supply(
    val circulating: String? = "",
    val confirmed: Boolean? = false,
    val max: String? = "",
    val supplyAt: Int? = 0,
    val total: String? = ""
)

data class AllTimeHigh(
    val price: String? = "",
    val timestamp: Int? = 0
)

data class Notice(
    val type: String? = "",
    val value: String? = ""
)