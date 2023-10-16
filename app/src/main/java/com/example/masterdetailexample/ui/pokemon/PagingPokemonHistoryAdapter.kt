package com.example.masterdetailexample.ui.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.masterdetailexample.databinding.ItemPokemonBinding
import com.example.masterdetailexample.domain.models.DataPaging
import com.example.masterdetailexample.ui.pokemon.PagingPokemonHistoryAdapter.PagingViewHolder

class PagingPokemonHistoryAdapter :
    PagingDataAdapter<DataPaging, PagingViewHolder>(diffCallback){

    inner class PagingViewHolder(
        val binding: ItemPokemonBinding
    ) : ViewHolder(binding.root) {
        fun bind(data : DataPaging?){
            data?.let {
                binding.tvDataNamePokemon.text = it.name
            }

            binding.bFav.setOnClickListener {
                onFavoriteClickListener?.let { click ->
                    click(data)
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DataPaging>() {
            override fun areItemsTheSame(oldItem: DataPaging, newItem: DataPaging): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataPaging, newItem: DataPaging): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    protected var onFavoriteClickListener : ((DataPaging?) -> Unit)? = null

    fun setFavoriteClickListener(listener: (DataPaging?) -> Unit) {
        onFavoriteClickListener = listener
    }
}