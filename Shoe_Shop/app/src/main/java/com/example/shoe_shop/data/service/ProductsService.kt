package com.example.shoe_shop.data.service

import com.example.shoe_shop.data.model.Category
import com.example.shoe_shop.data.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {

    @GET("rest/v1/products")
    suspend fun getProducts(
        @Query("select") select: String = "*",
        @Query("order") order: String = "created_at.desc"
    ): Response<List<Product>>

    @GET("rest/v1/products")
    suspend fun getBestSellers(
        @Query("select") select: String = "*",
        @Query("is_best_seller") isBestSeller: Boolean = true,
        @Query("order") order: String = "created_at.desc"
    ): Response<List<Product>>

    @GET("rest/v1/products")
    suspend fun getProductsByCategory(
        @Query("select") select: String = "*",
        @Query("category_id") categoryId: String,
        @Query("order") order: String = "created_at.desc"
    ): Response<List<Product>>
}

interface CategoriesService {

    @GET("rest/v1/categories")
    suspend fun getCategories(
        @Query("select") select: String = "*",
        @Query("order") order: String = "title.asc"
    ): Response<List<Category>>
}