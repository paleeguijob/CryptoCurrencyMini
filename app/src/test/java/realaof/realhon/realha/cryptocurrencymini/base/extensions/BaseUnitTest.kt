package realaof.realhon.realha.cryptocurrencymini.base.extensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseUnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @Before
    open fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }
}