package com.example.shoppingcart.di

import android.content.Context
import androidx.room.Room
import com.example.shoppingcart.data.local.ShoppingCartDao
import com.example.shoppingcart.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ShoppingDatabase::class.java, "shopping_db").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun providesDao(shoppingDatabase: ShoppingDatabase): ShoppingCartDao = shoppingDatabase.shoppingItemDao()

}