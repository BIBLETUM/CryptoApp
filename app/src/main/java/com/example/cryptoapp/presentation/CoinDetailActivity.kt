package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }
    private var fromSymbol = UNDEFINED_FROM_SYMBOL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        parseIntent()

        if (savedInstanceState == null && supportFragmentManager.fragments.isEmpty()) {
            val fragment = CoinDetailFragment.newInstance(fromSymbol)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.coin_info_container, fragment)
                .commit()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(FROM_SYMBOL_EXTRA)) {
            throw RuntimeException("Param from symbol is absent")
        }
        val fromSymb = intent.getStringExtra(FROM_SYMBOL_EXTRA)
        if (fromSymb == UNDEFINED_FROM_SYMBOL || fromSymb == null){
            throw RuntimeException("Undefined from symbol")
        }
        fromSymbol = fromSymb
    }

    companion object {
        private const val FROM_SYMBOL_EXTRA = "fromSymbol"
        private const val UNDEFINED_FROM_SYMBOL = "undefined"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYMBOL_EXTRA, fromSymbol)
            return intent
        }
    }
}