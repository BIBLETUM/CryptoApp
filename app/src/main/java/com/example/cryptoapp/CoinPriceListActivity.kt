package com.example.cryptoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.adapters.CoinInfoAdapter
import com.example.cryptoapp.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        initViews()
        val coinInfoAdapter = CoinInfoAdapter(this)
        recyclerView.adapter = coinInfoAdapter
        coinInfoAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = Intent(this@CoinPriceListActivity, CoinDetailActivity::class.java)
                intent.putExtra(CoinDetailActivity.FROM_SYMBOL_EXTRA, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.priceLis.observe(this, Observer {
            coinInfoAdapter.listCoins = it
        })
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewCoinPriceList)
    }
}