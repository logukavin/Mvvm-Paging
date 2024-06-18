package com.example.mvvm.api

import com.example.mvvm.entities.UserDetailsResponse
import com.example.mvvm.entities.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET(ApiConstants.API_USER)
    suspend fun getUser(
        @Query(ApiConstants.LIMIT) limit: Int,
        @Query(ApiConstants.SKIP) skip: Int,
    ): Response<UserListResponse>

    @GET(ApiConstants.API_USER_DETAILS+"{id}")
    suspend fun getUserDetails(@Path("id") userId: Int): UserDetailsResponse
}