package com.example.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.CartLayoutRowBinding
import com.example.shoppingcart.model.Item

class CartAdapter(private var cartList: List<Item> = emptyList()): RecyclerView.Adapter<CartAdapter.ItemViewholder>() {

    inner class ItemViewholder(private val binding: CartLayoutRowBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(item: Item) {
            binding.apply {
                itemName.text = item.itemName
                val itemCount = item.itemCount
                val itemPrice = item.sellingPrice
                val taxPercentage = item.taxPercentage
                val totalPrice = itemCount * itemPrice * (1 + taxPercentage / 100)
                val formattedPrice = String.format("%.1f", totalPrice)

                itemPriceIncTax.text = formattedPrice
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewholder(CartLayoutRowBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val currentItem = cartList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun setItem(items: List<Item>) {
        cartList = items
        notifyDataSetChanged()
    }
}
