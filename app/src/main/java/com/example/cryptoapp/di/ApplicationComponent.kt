package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.presentation.CoinDetailFragment
import com.example.cryptoapp.presentation.CoinPriceListActivity
import com.example.cryptoapp.presentation.CryptoApplication
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ModuleData::class, ModuleViewModel::class, ModuleWorker::class])
interface ApplicationComponent {
    fun inject(activity: CoinPriceListActivity)
    fun inject(fragment: CoinDetailFragment)
    fun inject(application: CryptoApplication)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}