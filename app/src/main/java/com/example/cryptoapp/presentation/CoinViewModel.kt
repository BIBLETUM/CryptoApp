package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.GetCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getCoinDetailUseCase = GetCoinInfoUseCase(repository)

    val priceList = getCoinInfoListUseCase()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String) = getCoinDetailUseCase(fSym)

    private fun loadData() {
        loadDataUseCase()
    }
}