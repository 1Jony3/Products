package com.example.products.model.data.api

import com.example.products.model.data.entities.Product
import com.example.products.model.data.entities.ProductResponse
import com.example.products.model.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IProductAPI {
    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: String
        ): Product
    @GET("/products/search")
    suspend fun getSearchBy(
        @Query("skip") skip: String?,
        @Query("limit") limit: String? = Constants.PAGE_SIZE.toString(),
        @Query("q") q : String?
    ): ProductResponse
}