package realaof.realhon.realha.cryptocurrencymini.domian.repository

import app.cash.turbine.turbineScope
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import realaof.realhon.realha.cryptocurrencymini.base.extensions.BaseUnitTest
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Data
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Stats
import realaof.realhon.realha.cryptocurrencymini.data.service.CoinCurrencyService
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CoinCurrencyRepositoryTest : BaseUnitTest() {

    private val coinCurrencyService: CoinCurrencyService = mockk()

    private lateinit var repository: CoinCurrencyRepository

    private val dispatcher = Dispatchers.Default

    @Before
    override fun setup() {
        super.setup()

        repository = CoinCurrencyRepositoryImp(coinCurrencyService, dispatcher)
    }

    @Test
    fun getCoinList_Success() = runTest {
        //Given
        val coinCurrency = getCurrency()
        val mockResponse = NetworkResponse.Success(coinCurrency)

        //When
        coEvery { coinCurrencyService.getCoinList() } returns mockResponse

        //Then
        turbineScope {
            val response = repository.getCoinList(offset = 0, limit = 20).testIn(backgroundScope)
            assertEquals(mockResponse, response.awaitItem())
            response.awaitComplete()
        }
    }

    @Test
    fun getCoinList_ExceptionEmitFlow() = runTest {
        //Given
        val mockResponse = IOException()

        //When
        //Don't mock any response from service,It's for test emit Flow catch exception

        //Then
        turbineScope {
            repository.getCoinList(limit = 20, offset = 0).testIn(backgroundScope)
        }
    }

    @Test
    fun getCoinSearch_Success() = runTest {
        //Given
        val keyword = "coin currency"
        val offset = 0
        val coinCurrency = getCurrency()
        val mockResponse = NetworkResponse.Success(coinCurrency)

        //When
        coEvery {
            coinCurrencyService.searchCoin(
                keyword = keyword,
                offset = offset
            )
        } returns mockResponse

        //Then
        turbineScope {
            val response =
                repository.searchCoin(keyword = keyword, offset = offset).testIn(backgroundScope)
            assertEquals(mockResponse, response.awaitItem())
        }
    }

    @Test
    fun getCoinSearch_ExceptionEmitFlow() = runTest {
        //Given
        val keyword = "coin currency"
        val offset = 0
        val mockResponse = IOException()

        //When
        //Don't mock any response from service,It's for test emit Flow catch exception

        //Then
        turbineScope {
            repository.searchCoin(keyword, offset).testIn(backgroundScope)
        }
    }

    @Test
    fun getCoinDetail_Success() = runTest {
        //Given
        val uuid = "uuid"
        val coinDetail = getCoinDetail()
        val mockResponse = NetworkResponse.Success(coinDetail)

        //When
        coEvery { coinCurrencyService.getCoinDetail(uuid = uuid) } returns mockResponse

        //Then
        turbineScope {
            val response = repository.getCoinDetail(uuid = uuid).testIn(backgroundScope)
            assertEquals(mockResponse, response.awaitItem())
            response.awaitComplete()
        }
    }

    @Test
    fun getCoinDetail_ExceptionEmitFlow() = runTest {
        //Given
        val uuid = "uuid"

        //When
        //Don't mock any response from service,It's for test emit Flow catch exception

        //Then
        turbineScope {
            repository.getCoinDetail(uuid = uuid).testIn(backgroundScope)
        }
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

    private fun getCoinDetail() = CoinDetail(
        data = realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Data(
            coin = realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Coin()
        ),
        status = "success"
    )
}