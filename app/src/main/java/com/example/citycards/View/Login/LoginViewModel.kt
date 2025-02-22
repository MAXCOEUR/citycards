package com.example.citycards.View.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.City
import com.example.citycards.Model.LoginUser
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.Repository.CityRepository
import com.example.citycards.Repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    fun loginUser(user: LoginUser): LiveData<User> {
        var livedata = MutableLiveData<User>()
        viewModelScope.launch {
            val data = UserRepository.loginUser(user)
            data.collect {user->
                Log.e("TAG", "livedata.postValue(user)" )
                livedata.postValue(user)
            }

        }

        return livedata
    }

}