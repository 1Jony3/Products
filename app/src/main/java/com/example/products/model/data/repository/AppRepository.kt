package com.example.products.model.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.products.model.data.api.IProductAPI
import com.example.products.model.data.entities.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val products: IProductAPI): Repository {


    override suspend fun getPagedProducts(): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductsRemotePagingSource(products) }
        ).flow
    }

}