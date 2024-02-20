package realaof.realhon.realha.cryptocurrencymini.ui.screen.detail

import app.cash.turbine.turbineScope
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import realaof.realhon.realha.cryptocurrencymini.base.extensions.BaseUnitTest
import realaof.realhon.realha.cryptocurrencymini.base.model.toBaseCommonError
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.Data
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapperImp
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinDetailUseCase
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailBottomSheetViewModelTest : BaseUnitTest() {

    private val getCoinDetailUseCase: GetCoinDetailUseCase = mockk()
    private val coinCurrencyMapper: CoinCurrencyMapperImp = CoinCurrencyMapperImp()

    private lateinit var viewModel: CoinDetailBottomSheetViewModel

    @Before
    override fun setup() {
        super.setup()

        viewModel = CoinDetailBottomSheetViewModel(
            getCoinDetailUseCase = getCoinDetailUseCase,
            coinCurrencyMapper = coinCurrencyMapper
        )
    }

    @Test
    fun getCoinDetail_Success() = runTest {
        //Given
        val uuid = "uuid"
        val coinDetail = getCoinDetail()
        val expectData =
            CoinDetailUiState(success = coinCurrencyMapper.mapToCoinDetailUi(coinDetail))

        coEvery { getCoinDetailUseCase.execute(GetCoinDetailUseCase.Input(uuid)) } returns Result.success(
            coinDetail
        )

        //When
        viewModel.getCoinDetail(uuid)

        //Then
        turbineScope {
            val response = viewModel.coinDetailBottomSheetState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinDetail_Failure() = runTest {
        //Given
        val uuid = "uuid"
        val error = Throwable()
        val expectData = CoinDetailUiState(error = error.toBaseCommonError())

        coEvery { getCoinDetailUseCase.execute(GetCoinDetailUseCase.Input(uuid)) } returns Result.failure(
            error
        )

        //When
        viewModel.getCoinDetail(uuid)

        //Then
        turbineScope {
            val response = viewModel.coinDetailBottomSheetState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun coinDetail_clearCoinDetail() = runTest {
        //Given
        val uuid = "uuid"
        val coinDetail = getCoinDetail()
        val expectData = CoinDetailUiState()

        coEvery { getCoinDetailUseCase.execute(GetCoinDetailUseCase.Input(uuid)) } returns Result.success(
            coinDetail
        )

        //When
        viewModel.getCoinDetail(uuid)
        viewModel.clearCoinDetailStateValue()

        //Then
        turbineScope {
            val response = viewModel.coinDetailBottomSheetState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    private fun getCoinDetail() = CoinDetail(
        data = Data(
            coin = Coin()
        ),
        status = "success"
    )
}