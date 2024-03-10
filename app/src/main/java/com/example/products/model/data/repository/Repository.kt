package com.example.products.model.data.repository

import androidx.paging.PagingData
import com.example.products.model.data.entities.Product
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPagedProducts(): Flow<PagingData<Product>>
}