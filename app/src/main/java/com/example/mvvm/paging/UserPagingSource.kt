package com.example.mvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvm.api.ApiConstants
import com.example.mvvm.api.AppApi
import com.example.mvvm.entities.UsersItem

class UserPagingSource(private val apiService: AppApi) :
    PagingSource<Int, UsersItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersItem> {

        return try {
            val page = params.key ?: 0
            val skip = page * ApiConstants.NETWORK_PAGE_SIZE
            val limit = ApiConstants.NETWORK_PAGE_SIZE

            val response = apiService.getUser(limit, skip)
            LoadResult.Page(
                data = response.body()!!.users, prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.body()!!.users.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UsersItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
