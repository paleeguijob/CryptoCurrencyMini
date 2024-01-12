package realaof.realhon.realha.cryptocurrencymini.domian.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import realaof.realhon.realha.cryptocurrencymini.base.extensions.BaseUnitTest
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Data
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Stats
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinListUseCaseTest : BaseUnitTest() {

    private val repository: CoinCurrencyRepository = mockk()

    private lateinit var getCoinListUseCase: GetCoinListUseCase

    @Before
    override fun setup() {
        super.setup()

        getCoinListUseCase = GetCoinListUseCase(repository)
    }

    @Test
    fun getCoinListUseCase() = runTest {
        //Given
        val limit = 20
        val offset = 0
        val coinCurrency = getCurrency()
        val mockResponse = flowOf(NetworkResponse.Success(coinCurrency))

        coEvery { repository.getCoinList(limit = limit, offset = offset) } returns mockResponse

        //When
        val response = getCoinListUseCase.execute(GetCoinListUseCase.Input(limit, offset))

        //Then
        assertEquals(coinCurrency, response.getOrNull())
    }

    private fun getCurrency() = CoinCurrency(
        data = Data(
            coins = listOf(Coin()),
            stats = Stats(
                total = 100,
                total24hVolume = "",
                totalCoins = 100,
                totalExchanges = 100,
                totalMarketCap = "",
                totalMarkets = 100
            )
        ),
        status = "success"
    )
}