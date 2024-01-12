package realaof.realhon.realha.cryptocurrencymini.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import realaof.realhon.realha.cryptocurrencymini.data.service.CoinCurrencyService
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideCoinCurrencyService(retrofit: Retrofit): CoinCurrencyService =
        retrofit.create(CoinCurrencyService::class.java)
}