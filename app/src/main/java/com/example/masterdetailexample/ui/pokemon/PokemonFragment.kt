package com.example.masterdetailexample.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterdetailexample.databinding.FragmentPokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonFragment : Fragment() {

    private val  binding: FragmentPokemonBinding by lazy {
        FragmentPokemonBinding.inflate(layoutInflater)
    }
    private lateinit var mAdapter : PagingPokemonHistoryAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private val viewModel: PokemonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setClickListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    private fun initUi(){
        with(binding){
            mAdapter = PagingPokemonHistoryAdapter()
            mLayoutManager = LinearLayoutManager(requireContext())
            binding.recycler.apply {
                adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = PokemonHistoryLoadStateAdapter {mAdapter.retry()},
                    footer = PokemonHistoryLoadStateAdapter {mAdapter.retry()}
                )
                layoutManager = mLayoutManager
            }
            getPokemon()
        }
    }

    private fun getPokemon(){
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setClickListeners() {
        mAdapter.apply {
            setFavoriteClickListener{
                it?.let {
                    if (it.isFavorite) viewModel.deleteFavorite(it.name) else viewModel.saveFavorite(it.name)
                }
            }
        }
    }
}