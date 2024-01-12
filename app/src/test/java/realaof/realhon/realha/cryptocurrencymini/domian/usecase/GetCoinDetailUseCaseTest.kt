package realaof.realhon.realha.cryptocurrencymini.domian.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import realaof.realhon.realha.cryptocurrencymini.base.extensions.BaseUnitTest
import realaof.realhon.realha.cryptocurrencymini.base.model.NetworkResponse
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Data
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinDetailUseCaseTest : BaseUnitTest() {

    private val repository: CoinCurrencyRepository = mockk()

    private lateinit var getCoinDetailUseCase: GetCoinDetailUseCase

    @Before
    override fun setup() {
        super.setup()

        getCoinDetailUseCase = GetCoinDetailUseCase(repository)
    }

    @Test
    fun getCoinDetailUseCase() = runTest {
        //Given
        val uuid = "uuid"
        val coinDetail = getCoinDetail()
        val mockResponse = flowOf(NetworkResponse.Success(coinDetail))

        coEvery { repository.getCoinDetail(uuid = uuid) } returns mockResponse

        //When
        val response = getCoinDetailUseCase.execute(GetCoinDetailUseCase.Input(uuid))

        //Then
        Assert.assertEquals(coinDetail, response.getOrNull())
    }


    private fun getCoinDetail() = CoinDetail(
        data = Data(
            coin = Coin()
        ),
        status = "success"
    )
}