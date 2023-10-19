package com.example.citycards.View.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.CityList
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Repository.CityRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun getCitysSearch(dataQuery: QueryDataCity): LiveData<Response<CityList>> {
        var livedata = MutableLiveData<Response<CityList>>()
        viewModelScope.launch {
            CityRepository.getCitysSearch(dataQuery)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }
    fun getCitysCollection(dataQuery: QueryDataCity): LiveData<Response<CityList>> {
        var livedata = MutableLiveData<Response<CityList>>()
        viewModelScope.launch {
            CityRepository.getCityCollection(dataQuery)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }

    fun getCitysRandom(offset:Int,minPop:Int, maxPop:Int): LiveData<Response<CityList>> {
        var livedata = MutableLiveData<Response<CityList>>()
        viewModelScope.launch {
            CityRepository.getCityRandom(offset,minPop,maxPop)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }
}