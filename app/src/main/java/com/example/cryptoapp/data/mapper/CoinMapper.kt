package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.lastMarket,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        BASE_IMAGE_URL + dto.imageUrl
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
        String.format("%.6f", model.price),
        convertTimeStampToString(model.lastUpdate),
        String.format("%.6f", model.highDay),
        String.format("%.6f", model.lowDay),
        model.imageUrl
    )

    private fun convertTimeStampToString(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}