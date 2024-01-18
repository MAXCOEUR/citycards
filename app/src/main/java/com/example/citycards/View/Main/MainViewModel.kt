package com.example.citycards.View.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.Repository.CityRepository
import com.example.citycards.Repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    fun getCitysCollection_favori(dataQuery: QueryDataCity): LiveData<Response<CityList>> {
        var livedata = MutableLiveData<Response<CityList>>()
        viewModelScope.launch {
            CityRepository.getCityCollection_favori(dataQuery)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }

    fun setFavori(City: City): LiveData<City> {
        var livedata = MutableLiveData<City>()
        viewModelScope.launch {
            CityRepository.setFavori(City)
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }
        return livedata
    }

    fun getRegion(): LiveData<Response<List<String>>> {
        var livedata = MutableLiveData<Response<List<String>>>()
        viewModelScope.launch {
            CityRepository.getRegion()
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }

    fun getRang(): LiveData<Response<List<String>>> {
        var livedata = MutableLiveData<Response<List<String>>>()
        viewModelScope.launch {
            CityRepository.getRank()
                .catch {
                    Log.e("erreur",it.toString())
                }
                .collect{
                    livedata.postValue(it)
                }
        }

        return livedata
    }

    fun updateUser(user:User): LiveData<User>{
        var livedata = MutableLiveData<User>()
        viewModelScope.launch {
            UserRepository.updateUser(user)
                .catch {
                    Log.e("erreur update",it.toString())
                }.collect{
                    livedata.postValue(it)
                }
        }
        return livedata
    }

    fun deleteCity(city: City): LiveData<City>{
        var livedata = MutableLiveData<City>()
        viewModelScope.launch {
            CityRepository.deleteCity(city)
                .catch {
                    Log.e("erreur update",it.toString())
                }.collect{
                    livedata.postValue(it)
                }
        }
        updateUser(UserRepository.getUserLogin())
        return livedata
    }
}