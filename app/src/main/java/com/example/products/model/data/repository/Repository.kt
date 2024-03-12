package com.example.products.model.data.repository

import androidx.paging.PagingData
import com.example.products.model.data.entities.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    suspend fun getPagedProducts(): Flow<PagingData<Product>>
    suspend fun getProduct(id: String): Product
}