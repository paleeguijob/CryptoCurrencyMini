package realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency

import com.google.gson.annotations.SerializedName

data class CoinCurrency(
    @SerializedName("data") val data: Data,
    @SerializedName("status") val status: String
)

data class Data(
    @SerializedName("coins") val coins: List<Coin> = emptyList(),
    @SerializedName("stats") val stats: Stats
)

data class Stats(
    @SerializedName("data") val total: Int,
    @SerializedName("total24hVolume") val total24hVolume: String,
    @SerializedName("totalCoins") val totalCoins: Int,
    @SerializedName("totalExchanges") val totalExchanges: Int,
    @SerializedName("totalMarketCap") val totalMarketCap: String,
    @SerializedName("totalMarkets") val totalMarkets: Int
)

data class Coin(
    @SerializedName("uuid") val uuid: String? = "",
    @SerializedName("symbol") val symbol: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("color") val color: String? = "",
    @SerializedName("iconUrl") val iconUrl: String? = "",
    @SerializedName("marketCap") val marketCap: String? = "",
    @SerializedName("price") val price: String? = "",
    @SerializedName("btcPrice") val btcPrice: String? = "",
    @SerializedName("listedAt") val listedAt: Int? = 0,
    @SerializedName("tier") val tier: Int? = 0,
    @SerializedName("change") val change: String? = "",
    @SerializedName("rank") val rank: Int? = 0,
    @SerializedName("sparkline") val sparkline: List<String>? = emptyList(),
    @SerializedName("coinrankingUrl") val coinrankingUrl: String? = "",
    @SerializedName("24hVolume") val `24hVolume`: String? = ""
)