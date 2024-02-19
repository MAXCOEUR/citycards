package com.example.citycards.View.SplashScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.Repository.CityRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {
    fun getData(): LiveData<Boolean> {
        var livedata = MutableLiveData<Boolean>()

        viewModelScope.launch{
            var nbrOcc = 5;
            fun semaphore_login(){
                nbrOcc--
                if(nbrOcc==0){
                    livedata.postValue(true)
                }

            }

            val dataQuery: QueryDataCity = QueryDataCity()

            dataQuery.limiteur=1

            dataQuery.minPop= City.PETITE_VILLE.first
            dataQuery.maxPop= City.PETITE_VILLE.second
            val data_petit_ville = CityRepository.getCitysSearch(dataQuery)
            data_petit_ville.collect{
                it.body()?.let {
                    City.NBR_PETITE_VILLE = it.metadata.totalCount
                    Log.e("TAG", "NBR_PETITE_VILLE: "+it.metadata.totalCount )
                }
                semaphore_login()
            }

            dataQuery.minPop= City.MOYENNE_VILLE.first
            dataQuery.maxPop= City.MOYENNE_VILLE.second
            Thread.sleep(1500)
            val data_moyenne_ville = CityRepository.getCitysSearch(dataQuery)
            data_moyenne_ville.collect{
                it.body()?.let {
                    City.NBR_MOYENNE_VILLE = it.metadata.totalCount
                    Log.e("TAG", "NBR_MOYENNE_VILLE: "+it.metadata.totalCount )
                }
                semaphore_login()
            }

            dataQuery.minPop= City.GRANDE_VILLE.first
            dataQuery.maxPop= City.GRANDE_VILLE.second
            Thread.sleep(1500)
            val data_grande_ville = CityRepository.getCitysSearch(dataQuery)
            data_grande_ville.collect{
                it.body()?.let {
                    City.NBR_GRANDE_VILLE = it.metadata.totalCount
                    Log.e("TAG", "NBR_GRANDE_VILLE: "+it.metadata.totalCount )
                }
                semaphore_login()
            }

            dataQuery.minPop= City.METROPOLE.first
            dataQuery.maxPop= City.METROPOLE.second
            Thread.sleep(1500)
            val data_metropole = CityRepository.getCitysSearch(dataQuery)
            data_metropole.collect{
                it.body()?.let {
                    City.NBR_METROPOLE = it.metadata.totalCount
                    Log.e("TAG", "NBR_METROPOLE: "+it.metadata.totalCount )
                }
                semaphore_login()
            }

            dataQuery.minPop= City.MEGAPOL.first
            dataQuery.maxPop= City.MEGAPOL.second
            Thread.sleep(1500)
            val data_megapol = CityRepository.getCitysSearch(dataQuery)
            data_megapol.collect{
                it.body()?.let {
                    City.NBR_MEGAPOL = it.metadata.totalCount
                    Log.e("TAG", "NBR_MEGAPOL: "+it.metadata.totalCount )
                }
                semaphore_login()
            }
        }

        return livedata
    }
}