package com.example.citycards.View.CreateUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citycards.Model.CreateUser
import com.example.citycards.Model.User
import com.example.citycards.Repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CreateUserViewModel: ViewModel() {
    fun createUser(createuser:CreateUser): LiveData<User> {
        var livedata = MutableLiveData<User>()
        viewModelScope.launch {
            val data = UserRepository.createUser(createuser)
            data.collect {
                livedata.postValue(it)
            }

        }

        return livedata
    }
}