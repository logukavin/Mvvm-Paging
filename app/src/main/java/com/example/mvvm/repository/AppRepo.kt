package com.example.mvvm.repository


import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mvvm.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.mvvm.api.AppApi
import com.example.mvvm.entities.UserDetailsResponse
import com.example.mvvm.entities.UsersItem
import com.example.mvvm.extensions.NetworkResult
import com.example.mvvm.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AppRepo @Inject constructor(private val appApi: AppApi) {

    fun getAllUser(): LiveData<PagingData<UsersItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = 2
            ),
            pagingSourceFactory = { UserPagingSource(appApi) }, initialKey = 1
        ).liveData
    }


    suspend fun getUserDetails(userId: Int): Flow<NetworkResult<UserDetailsResponse>> = flow {
        emit(NetworkResult.Loading(true))
        val response = appApi.getUserDetails(userId)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }

}