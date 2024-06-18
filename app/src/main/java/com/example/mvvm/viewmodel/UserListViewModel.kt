package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.entities.UsersItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor() : BaseViewModel() {
    val errorMessage = MutableLiveData<String>()

    fun getUserList(): LiveData<PagingData<UsersItem>> {
        return appRepo.getAllUser().cachedIn(viewModelScope)
    }

}