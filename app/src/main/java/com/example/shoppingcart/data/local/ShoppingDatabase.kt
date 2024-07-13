package com.example.shoppingcart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingcart.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase: RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingCartDao

}