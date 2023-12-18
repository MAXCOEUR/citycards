package com.example.citycards.View.TirageCard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import com.example.citycards.Repository.CityRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response

class TirageCardActivityViewModel: ViewModel() {
    fun getCitysRandom(limit:Int, offset:Int,minPop:Int, maxPop:Int): LiveData<Response<CityList>> {
        var livedata = MutableLiveData<Response<CityList>>()
        viewModelScope.launch {
            CityRepository.getCityRandom(limit, offset,minPop,maxPop)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }
        return livedata
    }
    fun addCity(city: City): LiveData<City>{
        var livedata = MutableLiveData<City>()
        viewModelScope.launch {
            CityRepository.addCity(city)
        }
        return livedata
    }
}