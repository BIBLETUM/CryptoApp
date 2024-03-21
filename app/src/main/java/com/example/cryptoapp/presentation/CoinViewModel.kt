package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.domain.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.GetCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val getCoinDetailUseCase: GetCoinInfoUseCase
) : ViewModel() {

    val priceList = getCoinInfoListUseCase()

    init {
        loadDataUseCase()
    }

    fun getDetailInfo(fSym: String) = getCoinDetailUseCase(fSym)
}