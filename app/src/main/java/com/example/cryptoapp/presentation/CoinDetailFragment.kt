package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.data.pojo.CoinPriceInfo
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private lateinit var viewModel: CoinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val args = requireArguments()
        if (!args.containsKey(FROM_SYMBOL)) {
            throw RuntimeException("Param from symbol is absent")
        }

        val fromSymbol = args.getString(FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
                setData(it)
            }
        }
    }

    private fun setData(coinPriceInfo: CoinPriceInfo) {
        with(binding) {
            Picasso.get().load(coinPriceInfo.getFormattedImageUrl()).into(imageViewCoin)
            textViewFromSymbol.text = coinPriceInfo.fromSymbol
            textViewToSymbol.text = coinPriceInfo.toSymbol
            tvPrice.text = coinPriceInfo.price.toString()
            tvMinPrice.text = coinPriceInfo.lowDay.toString()
            tvMaxPrice.text = coinPriceInfo.highDay.toString()
            tvLastMarket.text = coinPriceInfo.lastMarket
            tvLastUpdate.text = coinPriceInfo.getFormattedTime()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FROM_SYMBOL = "fromSymbol"

        fun newInstance(fromSymbol: String): CoinDetailFragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }

}