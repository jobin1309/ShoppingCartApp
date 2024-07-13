package com.example.shoppingcart.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "shopping_table")
data class Item(
    @SerializedName("itemID")
    @PrimaryKey(autoGenerate = false) val itemID: String,
    @SerializedName("itemName")
    val itemName: String,
    @SerializedName("sellingPrice")
    val sellingPrice: Double,
    @SerializedName("taxPercentage")
    val taxPercentage: Double,
    val itemCount: Int,
    val itemTotal: Double
)