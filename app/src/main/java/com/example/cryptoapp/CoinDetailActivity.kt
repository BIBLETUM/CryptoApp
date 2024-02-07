package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var imageViewCoin: ImageView
    private lateinit var textViewFromSymbol: TextView
    private lateinit var textViewToSymbol: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvMinPrice: TextView
    private lateinit var tvMaxPrice: TextView
    private lateinit var tvLastMarket: TextView
    private lateinit var tvLastUpdate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if(!intent.hasExtra(FROM_SYMBOL_EXTRA)){
            finish()
            return
        }
        initViews()
        val fromSymbol = intent.getStringExtra(FROM_SYMBOL_EXTRA)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
                setData(it)
            })
        }

    }

    private fun setData(coinPriceInfo: CoinPriceInfo){
        Picasso.get().load(coinPriceInfo.getFormattedImageUrl()).into(imageViewCoin)
        textViewFromSymbol.text = coinPriceInfo.fromSymbol
        textViewToSymbol.text = coinPriceInfo.toSymbol
        tvPrice.text = coinPriceInfo.price.toString()
        tvMinPrice.text = coinPriceInfo.lowDay.toString()
        tvMaxPrice.text = coinPriceInfo.highDay.toString()
        tvLastMarket.text = coinPriceInfo.lastMarket
        tvLastUpdate.text = coinPriceInfo.getFormattedTime()
    }

    private fun initViews() {
        imageViewCoin = findViewById(R.id.imageViewCoin)
        textViewFromSymbol = findViewById(R.id.textViewFromSymbol)
        textViewToSymbol = findViewById(R.id.textViewToSymbol)
        tvPrice = findViewById(R.id.tvPrice)
        tvMinPrice = findViewById(R.id.tvMinPrice)
        tvMaxPrice = findViewById(R.id.tvMaxPrice)
        tvLastMarket = findViewById(R.id.tvLastMarket)
        tvLastUpdate = findViewById(R.id.tvLastUpdate)
    }

    companion object {
        private const val FROM_SYMBOL_EXTRA = "fromSymbol"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYMBOL_EXTRA, fromSymbol)
            return intent
        }
    }
}