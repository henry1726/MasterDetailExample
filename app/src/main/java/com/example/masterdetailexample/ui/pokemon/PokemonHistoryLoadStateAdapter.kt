package com.example.masterdetailexample.ui.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailexample.databinding.ItemLoadingStateBinding
import com.example.masterdetailexample.ui.pokemon.PokemonHistoryLoadStateAdapter.PokemonLoadStateViewHolder

class PokemonHistoryLoadStateAdapter (
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonLoadStateViewHolder>() {

    inner class PokemonLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            if (loadState is LoadState.Error){
                binding.textViewError.text = loadState.error.localizedMessage
            }

            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            binding.textViewError.isVisible = loadState is LoadState.Error

            binding.buttonRetry.setOnClickListener {
                retry()
            }

            binding.progressbar.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: PokemonLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PokemonLoadStateViewHolder (
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry)

}