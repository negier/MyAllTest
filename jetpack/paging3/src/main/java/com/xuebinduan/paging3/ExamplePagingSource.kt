package com.xuebinduan.paging3

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay

class ExamplePagingSource() : PagingSource<Int, User>() {
//    @ExperimentalPagingApi
//    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
//        Log.e("TAG", "PagingSource getRefreshKey")
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        Log.e("TAG", "PagingSource load")
        try {
            val currentPage = (params.key ?: 1)
            Log.e("TAG", "current page: $currentPage")
//            val prevPageNumber = if (currentPage > 1) currentPage - 1 else null

            val data = DataRepository().getData(currentPage)

            val totalPage = 3
            var nextPageNumber = if (currentPage < totalPage) {
                currentPage + 1
            } else {
                //没有更多数据
                null
            }
//            val response = backend.searchUsers(query, nextPageNumber)

            return LoadResult.Page(
                data = data,
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(e)
        }
    }
}