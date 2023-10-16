package com.example.masterdetailexample.ui.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterdetailexample.common.Resource
import com.example.masterdetailexample.domain.models.FirebaseModel
import com.example.masterdetailexample.domain.repositories.MasterDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject
constructor(
    private val repository: MasterDetailRepository
): ViewModel() {

    private val _res = MutableLiveData<Resource<List<FirebaseModel>>>()
    val res get() = _res
    fun getAllPoints() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        try {
            repository.getAllPoints().let {
                Log.e("TAG", "getAllPoints: $it")
                _res.postValue(Resource.success(it))
            }
        }catch (e: HttpException) {
            _res.postValue(Resource.knownError("Oops, algo salió mal, intente nuevamente.", null))
        } catch (e: IOException) {
            _res.postValue(Resource.knownError("No podemos acceder al servidor, Couldn't reach server, check your internet connection.", null))
        }
    }

}