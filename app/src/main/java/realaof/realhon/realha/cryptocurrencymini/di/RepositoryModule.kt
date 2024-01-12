package realaof.realhon.realha.cryptocurrencymini.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapper
import realaof.realhon.realha.cryptocurrencymini.domian.mapper.CoinCurrencyMapperImp
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepository
import realaof.realhon.realha.cryptocurrencymini.domian.repository.CoinCurrencyRepositoryImp

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideCoinCurrencyRepository(repositoryImp: CoinCurrencyRepositoryImp): CoinCurrencyRepository

    @Binds
    abstract fun provideCoinCurrencyMapper(mapperImp: CoinCurrencyMapperImp): CoinCurrencyMapper
}