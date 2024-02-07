package com.example.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if(!intent.hasExtra(FROM_SYMBOL_EXTRA)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(FROM_SYMBOL_EXTRA)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
                Log.d("ABOAB", it.toString())
            })
        }

    }

    companion object {
        const val FROM_SYMBOL_EXTRA = "fromSymbol"
    }
}