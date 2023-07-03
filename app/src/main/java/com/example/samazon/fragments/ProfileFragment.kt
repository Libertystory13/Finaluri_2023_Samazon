package com.example.samazon.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.samazon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userNameTextView: TextView
    private lateinit var emailShow: TextView
    private lateinit var button: Button
    private  var profilePic: Uri? = null
    private lateinit var selectPic: ImageView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.upload)
        userNameTextView = view.findViewById(R.id.userName)
        emailShow = view.findViewById(R.id.emailShow)
        selectPic = view.findViewById(R.id.select)

        selectPic.setOnClickListener {

            selectImage()
        }

        button.setOnClickListener {

            uploadImage()

        }


        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val userName = it.child("userName").value
                val email = it.child("email").value
                val fileName = it.child("filename").value

                val ref = FirebaseStorage.getInstance().getReference("/Images/${fileName}")

                ref.downloadUrl.addOnSuccessListener { it1 ->
                    Glide
                        .with(this)
                        .load(it1)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_add_a_photo_24)
                        .into(selectPic)
                }
                userNameTextView.text = "სახელი: ${userName}"
                emailShow.text = "მეილი: ${email}"
            }
        }
    }

    private fun uploadImage() {
        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val userName = it.child("userName").value.toString()
                val email = it.child("email").value.toString()

                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/Images/${filename}")

                if (profilePic != null) {
                    ref.putFile(profilePic!!)
                    val userInfo = com.example.samazon.data.UserInfo(userName, email, filename)
                    db.child(auth.currentUser?.uid!!).setValue(userInfo)
                }else {
                    Toast.makeText(requireContext(), "აირჩიეთ პროფილის სურათი", Toast.LENGTH_SHORT).show()
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
            selectPic.setImageURI(profilePic)
        }

    }
}