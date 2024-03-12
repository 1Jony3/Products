package com.example.products.model.data.api

import com.example.products.model.data.entities.Product
import com.example.products.model.data.entities.ProductResponse
import com.example.products.model.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IProductAPI {
    //products?skip={skip}&limit={limit}
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: String?,
        @Query("limit") limit: String? = Constants.PAGE_SIZE.toString()
        ): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: String
        ): Product
}