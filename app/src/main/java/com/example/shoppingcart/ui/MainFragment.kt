package com.example.shoppingcart.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.R
import com.example.shoppingcart.adapter.CartItemClickListener
import com.example.shoppingcart.adapter.ShoppingAdapter
import com.example.shoppingcart.databinding.FragmentMainBinding
import com.example.shoppingcart.model.Item
import com.example.shoppingcart.viewmodels.ShoppingCartViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), CartItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    val mViewmodel: ShoppingCartViewmodel by viewModels()

    private lateinit var mAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAdapter = ShoppingAdapter(cartItemClickListener = this, context = requireContext())
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recylerViewSetUp()
        mViewmodel.getShoppingItems()

        mViewmodel.shoppingItemsResponse.observe(viewLifecycleOwner) { items  ->
            if (items != null) {
                Log.d("items", items.toString())
                mAdapter.setItem(items)
            }
        }

        binding?.root?.getViewById(R.id.floatingActionButton2)?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_cartFragment)
        }

    }

    private fun recylerViewSetUp() {
        val recyclerView = binding?.recyclerView
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = LinearLayoutManager(context)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAddToCartClicked(item: Item, itemCount: Int) {
        val item = Item(itemCount = itemCount, itemName = item.itemName, itemID = item.itemID, sellingPrice = item.sellingPrice, taxPercentage = item.taxPercentage, itemTotal = item.itemTotal)
        mViewmodel.insertCartItem(item)
    }

}


