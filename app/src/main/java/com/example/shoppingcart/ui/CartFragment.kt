package com.example.shoppingcart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.adapter.CartAdapter
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.model.Item
import com.example.shoppingcart.viewmodels.ShoppingCartViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val mViewmodel: ShoppingCartViewmodel by viewModels()
    private lateinit var mAdapter: CartAdapter

    private var itemTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = CartAdapter()
        binding.cartRecyclerView.adapter = mAdapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(context)

        mViewmodel.getCartItems()
        mViewmodel.cartItem?.observe(viewLifecycleOwner) { items ->
            Log.d("RoomItems", items.toString())
            mAdapter.setItem(items)
            updateTotalPrice(items)
        }
        binding.deleteBtn.setOnClickListener {
            mViewmodel.deleteItems()
        }
    }

//    override fun onAddToCartClicked(item: Item, itemCount: Int) {
//        val itemTotalPrice = item.sellingPrice * item.itemCount * (1 + item.taxPercentage / 100)
//        itemTotal += itemTotalPrice
//        Log.d("itemCount", item.itemCount.toString())
//        binding.totalPrice.text = String.format("%.1f", itemTotal)
//    }

    private fun updateTotalPrice(items: List<Item>) {
        itemTotal = items.sumOf { it.sellingPrice * it.itemCount }
        binding.totalPrice.text = String.format("$%.1f", itemTotal)
    }

}
