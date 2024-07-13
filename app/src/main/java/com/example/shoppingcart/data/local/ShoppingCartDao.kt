package com.example.shoppingcart.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingcart.model.Item
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM shopping_table")
    fun getAllItems(): Flow<List<Item>>

    @Query("DELETE FROM shopping_table")
    suspend fun deleteAll()

}