package com.example.samazon.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samazon.R
import com.example.samazon.adapter.Adapter
import com.example.samazon.data.ProductInfo
import com.google.firebase.database.*


class StoreFragment : Fragment(R.layout.fragment_store) {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storeRecyclerView: RecyclerView
    private lateinit var productArrayList: ArrayList<ProductInfo>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        storeRecyclerView = view.findViewById(R.id.storeRecyclerView)
        storeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        storeRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf()
        getProductData()

    }

    private fun getProductData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("products")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productsSnapshot in snapshot.children){
                        val product = productsSnapshot.getValue(ProductInfo::class.java)
                        productArrayList.add(product!!)
                    }

                    storeRecyclerView.adapter = Adapter(productArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addProduct){
            goToAddProductPage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToAddProductPage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("დიახ"){_,_->
            findNavController().navigate(R.id.addItemFragment)
        }
        builder.setNegativeButton("არა"){_,_->

        }
        builder.setTitle("დაამატეთ პროდუქტი")
        builder.setMessage("გსურთ პროდუქტის დამატება?")
        builder.create().show()

    }


}
