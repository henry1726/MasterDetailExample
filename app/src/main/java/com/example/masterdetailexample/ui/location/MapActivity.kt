package com.example.masterdetailexample.ui.location

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailexample.R
import com.example.masterdetailexample.common.Resource
import com.example.masterdetailexample.domain.models.FirebaseModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val list: ArrayList<FirebaseModel> = ArrayList()
    private val viewModel: LocationViewModel by viewModels()
    private var halfScreen = 0
    private lateinit var adapter : LocationsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initObserverGetAllPoints()
        viewModel.getAllPoints()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (list.isEmpty().not()){

            for (item in list){
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(item.lat.toDouble(), item.long.toDouble()))
                        .title("Marker ${item.timeLocation}")
                )
            }

            showBottomSheet(list)
        }
    }

    private fun initObserverGetAllPoints(){
        viewModel.res.observe(this){
            when(it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let { model ->
                        Toast.makeText(this, "${model.first().lat.toDouble()}",Toast.LENGTH_SHORT).show()
                        list.addAll(model)
                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this)
                    }
                }
                Resource.Status.KNOWN_ERROR -> {

                }
                Resource.Status.LOADING -> {

                }
                else -> {}
            }
        }
    }

    private fun showBottomSheet(list: List<FirebaseModel>){
        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        val mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        val metrics = Resources.getSystem().displayMetrics
        halfScreen = (metrics.heightPixels / 2)
        windowManager.defaultDisplay.getMetrics(metrics)
        mBottomSheetBehavior.maxHeight = metrics.heightPixels + 500
        mBottomSheetBehavior.peekHeight = 120
        adapter = LocationsAdapter(list)
        bottomSheet.findViewById<RecyclerView>(R.id.rv_locations_main).adapter = adapter
//        bottomSheet.findViewById<RecyclerView>(R.id.rv_orders_main).setHasFixedSize(true)
        bottomSheet.findViewById<RecyclerView>(R.id.rv_locations_main).layoutManager = LinearLayoutManager(this)
        bottomSheet.findViewById<RecyclerView>(R.id.rv_locations_main).itemAnimator = DefaultItemAnimator()

    }
}