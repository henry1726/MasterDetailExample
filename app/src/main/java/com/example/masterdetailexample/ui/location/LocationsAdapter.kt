package com.example.masterdetailexample.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailexample.databinding.ItemLocationsBinding
import com.example.masterdetailexample.domain.models.FirebaseModel
import com.example.masterdetailexample.ui.location.LocationsAdapter.LocationHolder

class LocationsAdapter
    (private val list: List<FirebaseModel>) : RecyclerView.Adapter<LocationHolder>(){

    class LocationHolder(private val binding: ItemLocationsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: FirebaseModel){
            binding.tvDataCoordinate.text = "${data.lat}, ${data.long}"
            binding.tvDataTime.text = data.timeLocation
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        return LocationHolder(
            ItemLocationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}