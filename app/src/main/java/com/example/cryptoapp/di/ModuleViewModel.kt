package com.example.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ModuleViewModel {

    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    @Binds
    fun bindExampleViewModel(impl: CoinViewModel): ViewModel

}