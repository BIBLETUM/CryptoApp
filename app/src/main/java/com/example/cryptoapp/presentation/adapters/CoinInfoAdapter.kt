package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.presentation.CoinPriceItemDiffCallBack
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinPriceItemDiffCallBack) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coinInfo = getItem(position)
        val binding = holder.binding

        with(binding) {
            val symbsTemplate = context.resources.getString(R.string.symbs_template)
            val lastUpdateTemplate = context.resources.getString(R.string.date_of_last_update)
            tvSymbols.text = symbsTemplate.format(coinInfo.fromSymbol, coinInfo.toSymbol)
            tvPrice.text = coinInfo.price.toString()
            tvTimeUpdated.text =
                lastUpdateTemplate.format(coinInfo.lastUpdate)
            Picasso.get().load(coinInfo.imageUrl).into(imageViewCoin)

            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coinInfo)
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinInfoDto: CoinInfo)
    }
}