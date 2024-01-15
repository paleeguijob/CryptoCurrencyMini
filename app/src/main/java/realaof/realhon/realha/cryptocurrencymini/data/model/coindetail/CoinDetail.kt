package realaof.realhon.realha.cryptocurrencymini.data.model.coindetail

import com.google.gson.annotations.SerializedName

data class CoinDetail(
    @SerializedName("data") val data: Data,
    @SerializedName("status") val status: String
)

data class Data(
    @SerializedName("coin") val coin: Coin? = Coin()
)

data class Coin(
    @SerializedName("24hVolume") val `24hVolume`: String? = "",
    @SerializedName("allTimeHigh") val allTimeHigh: AllTimeHigh? = AllTimeHigh(),
    @SerializedName("btcPrice") val btcPrice: String? = "",
    @SerializedName("change") val change: String? = "",
    @SerializedName("coinrankingUrl") val coinrankingUrl: String? = "",
    @SerializedName("color") val color: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("fullyDilutedMarketCap") val fullyDilutedMarketCap: String? = "",
    @SerializedName("hasContent") val hasContent: Boolean? = false,
    @SerializedName("iconUrl") val iconUrl: String? = "",
    @SerializedName("links") val links: List<Link>? = emptyList(),
    @SerializedName("listedAt") val listedAt: Int? = 0,
    @SerializedName("lowVolume") val lowVolume: Boolean? = false,
    @SerializedName("marketCap") val marketCap: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("notices") val notices: List<Notice> = emptyList(),
    @SerializedName("numberOfExchanges") val numberOfExchanges: Int? = 0,
    @SerializedName("numberOfMarkets") val numberOfMarkets: Int? = 0,
    @SerializedName("price") val price: String? = "",
    @SerializedName("priceAt") val priceAt: Int? = 0,
    @SerializedName("rank") val rank: Int? = 0,
    @SerializedName("sparkline") val sparkline: List<String>? = emptyList(),
    @SerializedName("supply") val supply: Supply? = Supply(),
    @SerializedName("symbol") val symbol: String? = "",
    @SerializedName("tags") val tags: List<String>? = emptyList(),
    @SerializedName("tier") val tier: Int? = 0,
    @SerializedName("uuid") val uuid: String? = "",
    @SerializedName("websiteUrl") val websiteUrl: String? = ""
)

data class Link(
    @SerializedName("name") val name: String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("url") val url: String? = ""
)

data class Supply(
    @SerializedName("circulating") val circulating: String? = "",
    @SerializedName("confirmed") val confirmed: Boolean? = false,
    @SerializedName("max") val max: String? = "",
    @SerializedName("supplyAt") val supplyAt: Int? = 0,
    @SerializedName("total") val total: String? = ""
)

data class AllTimeHigh(
    @SerializedName("price") val price: String? = "",
    @SerializedName("timestamp") val timestamp: Int? = 0
)

data class Notice(
    @SerializedName("type") val type: String? = "",
    @SerializedName("value") val value: String? = ""
)