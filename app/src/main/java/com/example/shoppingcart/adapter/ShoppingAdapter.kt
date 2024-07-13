package com.example.shoppingcart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.ItemRowLayoutBinding
import com.example.shoppingcart.model.Item


class ShoppingAdapter(private var context: Context, private var itemList: List<Item> = emptyList(), private val cartItemClickListener: CartItemClickListener): RecyclerView.Adapter<ShoppingAdapter.ItemViewholder>(){

    private val itemClickCounts = mutableMapOf<Item, Int>()

    inner class ItemViewholder(private val binding: ItemRowLayoutBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(item: Item, itemCount: Int) {
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.sellingPrice.toString()
                addToCartBtn.text = "Add to Cart ($itemCount)"
                addToCartBtn.setOnClickListener {
                    val newCount = itemCount + 1
                    itemClickCounts[item] = newCount
                    cartItemClickListener.onAddToCartClicked(item, newCount)
                    bind(item, newCount)
                    Toast.makeText(context, "Item added", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewholder(ItemRowLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, itemClickCounts[currentItem] ?: 0 )
    }

    fun setItem(items: List<Item>) {
        itemList = items
        notifyDataSetChanged()
    }
}


interface CartItemClickListener {
    fun onAddToCartClicked(item: Item, itemCount: Int)
}