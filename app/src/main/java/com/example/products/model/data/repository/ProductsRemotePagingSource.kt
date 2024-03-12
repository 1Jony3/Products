package com.example.products.model.data.repository

import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.products.model.data.api.IProductAPI
import com.example.products.model.data.entities.Product
import com.example.products.model.utils.Constants
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRemotePagingSource @Inject constructor(
    private val productAPI: IProductAPI,
    private val searchBy: String
    ) : PagingSource<Int, Product>()
{
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: 0
        return try {
            val product = productAPI.getSearchBy(skip = position.toString(), q = searchBy)
            val nextKey = if (product.products.isEmpty()) {
                null
            } else {
                position + Constants.PAGE_SIZE
            }
            LoadResult.Page(
                data = product.products,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}