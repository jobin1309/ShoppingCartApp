package com.example.shoppingcart.data.remote

import com.example.shoppingcart.model.ShoppingItems
import retrofit2.Response
import retrofit2.http.GET

interface ShoppingCartApiService {

    @GET("/")
    suspend fun getItemList(): Response<ShoppingItems>

}