package com.example.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.entities.UserDetailsResponse
import com.example.mvvm.extensions.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor() : BaseViewModel() {

    val userDetailsResponse = MutableLiveData<NetworkResult<UserDetailsResponse>>()

    suspend fun getUserDetails(userId: Int) = withContext(Dispatchers.IO) {
        appRepo.getUserDetails(userId).collect {
            userDetailsResponse.postValue(it)
        }
    }


}