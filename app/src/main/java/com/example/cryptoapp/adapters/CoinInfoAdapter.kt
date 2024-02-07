package com.example.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var listCoins: List<CoinPriceInfo> = arrayListOf<CoinPriceInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coinInfo = listCoins[position]

        with(holder){
            val symbsTemplate = context.resources.getString(R.string.symbs_template)
            val lastUpdateTemplate = context.resources.getString(R.string.date_of_last_update)
            tvSymbols.text = symbsTemplate.format(coinInfo.fromSymbol, coinInfo.toSymbol)
            tvPrice.text = coinInfo.price.toString()
            tvTimeUpdated.text = lastUpdateTemplate.format(coinInfo.getFormattedTime())
            Picasso.get().load(coinInfo.getFormattedImageUrl()).into(imageViewCoin)

            itemView.setOnClickListener{
                onCoinClickListener?.onCoinClick(coinInfo)
            }
        }

    }

    override fun getItemCount(): Int = listCoins.size

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCoin = itemView.findViewById<ImageView>(R.id.imageViewCoin)
        val tvSymbols = itemView.findViewById<TextView>(R.id.tvSymbols)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvTimeUpdated = itemView.findViewById<TextView>(R.id.tvTimeUpdated)

    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}