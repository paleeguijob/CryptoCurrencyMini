package realaof.realhon.realha.cryptocurrencymini.ui.screen.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
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
import realaof.realhon.realha.cryptocurrencymini.data.model.coindetail.CoinDetail
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Coin
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.CoinCurrency
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Data
import realaof.realhon.realha.cryptocurrencymini.data.model.coinscurrency.Stats
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapperImp
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinDetailUseCase
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.GetCoinListUseCase
import realaof.realhon.realha.cryptocurrencymini.domian.usecase.SearchCoinUseCase
import realaof.realhon.realha.cryptocurrencymini.ui.screen.detail.uimodel.CoinDetailUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.LandingUiState
import realaof.realhon.realha.cryptocurrencymini.ui.screen.landing.uimodel.WindowSizeState
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@OptIn(ExperimentalCoroutinesApi::class)
class LandingViewModelTest : BaseUnitTest() {

    private val getCoinDetailUseCase: GetCoinDetailUseCase = mockk()

    private val getCoinListUseCase: GetCoinListUseCase = mockk()

    private val searchCoinUseCase: SearchCoinUseCase = mockk()

    private val coinCurrencyMapper = CoinCurrencyMapperImp()

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: LandingViewModel

    @Before
    override fun setup() = runTest {
        super.setup()

        viewModel = LandingViewModel(
            getCoinListUseCase = getCoinListUseCase,
            searchCoinUseCase = searchCoinUseCase,
            getCoinDetailUseCase = getCoinDetailUseCase,
            coinCurrencyMapper = coinCurrencyMapper,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun getCoinList_Success() = runTest {
        //Given
        val limit = 20
        val offset = 0
        val coinCurrency = getCurrency()
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(coinCurrency))

        coEvery {
            (getCoinListUseCase.execute(GetCoinListUseCase.Input(limit = limit, offset = offset)))
        } returns (Result.success(coinCurrency))

        //When
        viewModel.getCoinList(isLoadMore = false)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinList_SuccessCoinListEmpty() = runTest {
        //Given
        val limit = 20
        val offset = 0
        val coinCurrency = getEmptyCoins()
        val expectData = LandingUiState(empty = true)

        coEvery {
            getCoinListUseCase.execute(
                GetCoinListUseCase.Input(
                    limit = limit,
                    offset = offset
                )
            )
        } returns Result.success(coinCurrency)

        //When
        viewModel.getCoinList()

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinList_Failure() = runTest {
        val limit = 20
        val offset = 0
        val error = Throwable(message = "error message")
        val coinResponseMock = Result.failure<CoinCurrency>(exception = error)
        val expectData =
            LandingUiState(error = error.toBaseCommonError())

        coEvery {
            getCoinListUseCase.execute(
                GetCoinListUseCase.Input(
                    limit = limit,
                    offset = offset
                )
            )
        } returns coinResponseMock

        //When
        viewModel.getCoinList(limit)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinList_LoadMore_Success() = runTest {
        //Given
        val limit = 20
        val offset = 0
        val coinCurrency = getCurrency()
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(coinCurrency))

        coEvery {
            getCoinListUseCase.execute(
                GetCoinListUseCase.Input(
                    limit = limit,
                    offset = offset
                )
            )
        } returns Result.success(coinCurrency)

        //When
        viewModel.getCoinList(isLoadMore = true)
    }

    @Test
    fun loadMoreCoins_IndexMoreThanPageSize() = runTest {
        //Given
        val keyword = "Coin"
        val index = LandingViewModel.PAGE_SIZE - 1
        val coinCurrency = getCurrency()

        //expect data
        val expectCoinList = getCurrency().data.coins.toMutableList()
        expectCoinList.addAll(getCurrency().data.coins)
        val expectCoinCurrency = getCurrency().copy(
            data = coinCurrency.data.copy(
                coins = expectCoinList
            )
        )

        val mockCoinListResponse = Result.success(coinCurrency)
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(expectCoinCurrency))

        coEvery { searchCoinUseCase.execute(any()) } returns Result.success(
            coinCurrency
        )

        coEvery { getCoinListUseCase.execute(any()) } returns mockCoinListResponse

        //When
        viewModel.getCoinList()
        viewModel.loadMoreCoins(keyword, index)

        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun loadMoreCoins_IndexLessThanPageSize() = runTest {
        //Given
        val keyword = "Coin"
        val index = 0
        val expectData = LandingUiState(loading = true)

        //When
        viewModel.loadMoreCoins(keyword, index)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun loadMoreCoins_IndexMoreThanPageSize_KeywordIsEmpty() = runTest {
        //Given
        val keyword = ""
        val index = LandingViewModel.PAGE_SIZE - 1
        val coinCurrency = getCurrency()

        //expect data
        val expectCoinList = getCurrency().data.coins.toMutableList()
        expectCoinList.addAll(getCurrency().data.coins)
        val expectCoinCurrency = getCurrency().copy(
            data = coinCurrency.data.copy(
                coins = expectCoinList
            )
        )

        val mockCoinListResponse = Result.success(coinCurrency)
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(expectCoinCurrency))

        coEvery { searchCoinUseCase.execute(any()) } returns Result.success(
            coinCurrency
        )

        coEvery { getCoinListUseCase.execute(any()) } returns mockCoinListResponse

        //When
        viewModel.getCoinList()
        viewModel.loadMoreCoins(keyword, index)

        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinSearch_Success() = runTest {
        //Given
        val keyword = "Coins"
        val coinCurrency = getCurrency()
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(coinCurrency))

        coEvery { searchCoinUseCase.execute(any()) } returns Result.success(
            coinCurrency
        )

        //When
        viewModel.getSearchCoins(keyword)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun getCoinSearch_Failure() = runTest {
        //Given
        val keyword = "Coins"
        val error = Throwable()
        val expectData = LandingUiState(error = error.toBaseCommonError())

        coEvery { searchCoinUseCase.execute(any()) } returns Result.failure(
            error
        )

        //When
        viewModel.getSearchCoins(keyword)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun searchCoins_KeywordIsNotEmpty() = runTest {
        //Given
        val keyword = "Coins"
        val coinCurrency = getCurrency()
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(coinCurrency))

        coEvery { searchCoinUseCase.execute(any()) } returns Result.success(
            coinCurrency
        )

        //When
        viewModel.searchCoins(keyword)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
    }

    @Test
    fun searchCoins_KeywordIsEmpty() = runTest {
        //Given
        val limit = 20
        val offset = 0
        val keyword = ""
        val coinCurrency = getCurrency()
        val expectData =
            LandingUiState(success = coinCurrencyMapper.mapToCoinLandingUi(coinCurrency))

        coEvery {
            getCoinListUseCase.execute(
                GetCoinListUseCase.Input(
                    limit,
                    offset
                )
            )
        } returns Result.success(
            coinCurrency
        )

        //When
        viewModel.searchCoins(keyword)

        //Then
        turbineScope {
            val response = viewModel.landingUiState.testIn(backgroundScope).awaitItem()

            Assert.assertEquals(expectData.loading, response.loading)
            Assert.assertEquals(expectData.empty, response.empty)
            Assert.assertEquals(expectData.error, response.error)
            Assert.assertEquals(expectData.success, response.success)
        }
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
    fun setAdaptiveWindowSize_WindowWidthSizeCompact() = runTest {
        //Given
        val windowWidthSizeClass = WindowWidthSizeClass.Compact
        val expect = WindowSizeState(
            portrait = WindowSizeState.WindowAdaptive(
                adaptiveColumn = 1,
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
            )
        )

        //When
        viewModel.setAdaptiveWindowSizeSate(windowWidthSizeClass)

        //Then
        viewModel.windowAdaptiveState.test {
            Assert.assertEquals(expect, this.awaitItem())
        }
    }

    @Test
    fun setAdaptiveWindowSize_WindowWidthSizeMedium() = runTest {
        //Given
        val windowWidthSizeClass = WindowWidthSizeClass.Medium
        val expect = WindowSizeState(
            portrait = WindowSizeState.WindowAdaptive(
                adaptiveColumn = 1,
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen_8),
                verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
            )
        )

        //When
        viewModel.setAdaptiveWindowSizeSate(windowWidthSizeClass)

        //Then
        viewModel.windowAdaptiveState.test {
            Assert.assertEquals(expect, this.awaitItem())
        }
    }

    @Test
    fun setAdaptiveWindowSize_WindowWidthSizeExpand() = runTest {
        //Given
        val windowWidthSizeClass = WindowWidthSizeClass.Expanded
        val expect = WindowSizeState(
            landscape = WindowSizeState.WindowAdaptive(
                adaptiveColumn = 3,
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen_8),
                verticalArrangement = Arrangement.spacedBy(dimen.dimen_16)
            )
        )

        //When
        viewModel.setAdaptiveWindowSizeSate(windowWidthSizeClass)

        //Then
        viewModel.windowAdaptiveState.test {
            Assert.assertEquals(expect, this.awaitItem())
        }
    }

    private fun getCurrency() = CoinCurrency(
        data = Data(
            coins = listOf(
                Coin(
                    uuid = "uuid",
                    symbol = "SHIB",
                    name = "Shiba Inu",
                    color = "#fda32b",
                    iconUrl = "https://cdn.coinranking.com/fiZ4HfnRR/shib.png",
                    marketCap = "6072320102",
                    price = "0.000010300116041616",
                    listedAt = 1620650373,
                    change = "8.03",
                    rank = 20,
                    tier = 1
                )
            ),
            stats = Stats(
                total = 33754,
                totalCoins = 33754,
                totalMarkets = 40701,
                totalExchanges = 170,
                totalMarketCap = "1827927054000",
                total24hVolume = "224205772261"
            )
        ),
        status = "success"
    )

    private fun getEmptyCoins() = CoinCurrency(
        data = Data(
            coins = emptyList(),
            stats = Stats(
                total = 33754,
                total24hVolume = "224205772261",
                totalCoins = 33754,
                totalExchanges = 170,
                totalMarketCap = "1827927054000",
                totalMarkets = 40701
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