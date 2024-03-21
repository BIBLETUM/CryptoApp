package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDataBase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface ModuleData {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideExampleLocalDataSource(application: Application): CoinInfoDao {
            return AppDataBase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }


}