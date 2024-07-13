package com.example.shoppingcart.repository

import androidx.lifecycle.LiveData
import com.example.shoppingcart.data.local.ShoppingCartDao
import com.example.shoppingcart.data.remote.ShoppingCartApiService
import com.example.shoppingcart.model.Item
import com.example.shoppingcart.model.ShoppingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingCartRepo @Inject constructor(private val shoppingCartApi: ShoppingCartApiService, private val shoppingCartDao: ShoppingCartDao) {

    //Remote
    suspend fun getShoppingItems(): Flow<Response<ShoppingItems>> = flow {
        val response = shoppingCartApi.getItemList()
        emit(response)
    }

    //Local
    fun getCartItems(): Flow<List<Item>> {
        return shoppingCartDao.getAllItems()
    }

    suspend fun insertItem(item: Item) {
        shoppingCartDao.insertItem(item)
    }

    suspend fun deleteItems() {
        shoppingCartDao.deleteAll()
    }

}