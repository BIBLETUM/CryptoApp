package com.example.cryptoapp.domain

data class CoinInfo(
    val fromSymbol: String,
    val toSymbol: String?,
    val lastMarket: String?,
    val price: Double?,
    val lastUpdate: Long?,
    val highDay: Double?,
    val lowDay: Double?,
    val imageUrl: String?
)
