package com.example.shoppingcart.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.model.Item
import com.example.shoppingcart.model.ShoppingItems
import com.example.shoppingcart.repository.ShoppingCartRepo
import com.example.shoppingcart.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewmodel @Inject constructor(
    application: Application,
    private val shoppingCartRepo: ShoppingCartRepo
) : AndroidViewModel(application) {

    private val _shoppingItemsResponse: MutableLiveData<ShoppingItems?> = MutableLiveData()
    val shoppingItemsResponse: LiveData<ShoppingItems?> get() = _shoppingItemsResponse

    private val _cartItems: MutableLiveData<List<Item>>? = MutableLiveData()
    val cartItem: LiveData<List<Item>>? get() = _cartItems


    //Remote
    fun getShoppingItems() {
        viewModelScope.launch {
            try {
                shoppingCartRepo.getShoppingItems().collect { response ->
                    val shoppingResponse = handleShoppingResponse(response = response)
                    _shoppingItemsResponse.postValue(shoppingResponse.data)
                    Log.d("Items", shoppingResponse.data.toString())

                }
            } catch (e: Exception) {
                Log.d("Items", e.toString())
            }
        }
    }

    //local database cart
    fun getCartItems() {
        viewModelScope.launch {
            try {
                shoppingCartRepo.getCartItems().collect { cartItem ->
                    _cartItems?.value = cartItem
                }
            } catch (e: Exception) {

            }
        }
    }

    fun insertCartItem(item: Item) {
        viewModelScope.launch {
            shoppingCartRepo.insertItem(item)
        }
    }

    fun deleteItems() {
        viewModelScope.launch {
            shoppingCartRepo.deleteItems()
        }
    }

    //Handling API responses
    companion object {
        fun handleShoppingResponse(response: Response<ShoppingItems>): NetworkResult<ShoppingItems> {
            return when {
                response.body().isNullOrEmpty() -> {
                    NetworkResult.Error("Items not found")
                }
                response.isSuccessful -> {
                    val itemsResponse = response.body()
                    Log.d("Response", response.body().toString())
                    NetworkResult.Success(itemsResponse!!)
                }

                else -> {
                    NetworkResult.Error(response.message())
                }
            }
        }

    }
}