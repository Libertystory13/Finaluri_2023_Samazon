package com.example.samazon.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.samazon.ProfileActivity
import com.example.samazon.R
import com.example.samazon.data.ProductInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddItemFragment : Fragment(R.layout.fragment_add_item) {

    private lateinit var add: FloatingActionButton
    private lateinit var exit: FloatingActionButton
    private lateinit var productName: EditText
    private lateinit var productPrice: EditText
    private lateinit var productDescription: EditText
    private lateinit var productImage : ImageView
    private lateinit var selectImage : Button
    private  var profilePic: Uri? = null
    private val db = FirebaseDatabase.getInstance().getReference("products")
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add = view.findViewById(R.id.add)
        exit = view.findViewById(R.id.exit)
        productName = view.findViewById(R.id.productName)
        productPrice = view.findViewById(R.id.productPrice)
        productDescription = view.findViewById(R.id.productDescription)
        productImage = view.findViewById(R.id.productImage)
        selectImage = view.findViewById(R.id.selectImage)

        registerListeners()

    }

    private fun registerListeners() {
        exit.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ProfileActivity::class.java)
                it.startActivity(intent)
            }
        }

        selectImage.setOnClickListener{
            selectImage()

        }

        add.setOnClickListener {
            val fileName = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/ProductImages/${fileName}")
            if (profilePic != null) {
                ref.putFile(profilePic!!)
            }
            val name = productName.text.toString()
            val price = productPrice.text.toString()
            val description = productDescription.text.toString()

            val productInfo = ProductInfo(fileName, name, price, description)
            if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            }
            else {
                db.child(name).setValue(productInfo).addOnSuccessListener {
                    productName.text.clear()
                    productPrice.text.clear()
                    productDescription.text.clear()
                    Toast.makeText(requireContext(), "პროდუქტი დაემატა", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }


    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK){
            profilePic = data?.data!!
            productImage.setImageURI(profilePic)
        }

    }
}