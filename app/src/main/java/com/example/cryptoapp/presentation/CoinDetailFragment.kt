package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CryptoApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        val args = requireArguments()
        if (!args.containsKey(FROM_SYMBOL)) {
            throw RuntimeException("Param from symbol is absent")
        }

        val fromSymbol = args.getString(FROM_SYMBOL) ?: EMPTY_FROM_SYMBOL
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            setData(it)
        }
    }

    private fun setData(coinInfo: CoinInfo) {
        with(binding) {
            Picasso.get().load(coinInfo.imageUrl).into(imageViewCoin)
            textViewFromSymbol.text = coinInfo.fromSymbol
            textViewToSymbol.text = coinInfo.toSymbol
            tvPrice.text = coinInfo.price
            tvMinPrice.text = coinInfo.lowDay
            tvMaxPrice.text = coinInfo.highDay
            tvLastMarket.text = coinInfo.lastMarket
            tvLastUpdate.text = coinInfo.lastUpdate
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FROM_SYMBOL = "fromSymbol"
        private const val EMPTY_FROM_SYMBOL = ""

        fun newInstance(fromSymbol: String): CoinDetailFragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }

}