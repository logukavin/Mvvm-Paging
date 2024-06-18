package com.example.mvvm.base

import androidx.lifecycle.ViewModel
import com.example.mvvm.repository.AppRepo
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var appRepo: AppRepo

}