package com.example.products.model.data.api

import com.example.products.model.data.entities.ProductResponse
import com.example.products.model.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductAPI {
    //products?skip={skip}&limit={limit}
    @GET("products")
    suspend fun getProduct(
        @Query("skip") skip: String?,
        @Query("limit") limit: String? = Constants.PAGE_SIZE.toString()
        ): ProductResponse
}