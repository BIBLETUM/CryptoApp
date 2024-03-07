package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.lastMarket,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        dto.imageUrl
    )


    fun mapJsonContainerToListCoinInfo(container: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = container.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (key in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(key)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun namesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",").orEmpty()
    }

    fun mapDbModelToEntity(model: CoinInfoDbModel) = CoinInfo(
        model.fromSymbol,
        model.toSymbol,
        model.lastMarket,
        model.price,
        model.lastUpdate,
        model.highDay,
        model.lowDay,
        model.imageUrl
    )
}