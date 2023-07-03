package com.example.samazon.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.samazon.ChangePasswordActivity
import com.example.samazon.LoginActivity
import com.example.samazon.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var logOutButton: Button
    private lateinit var changePassword: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logOutButton = view.findViewById(R.id.buttonLogOut)
        changePassword = view.findViewById(R.id.changePassword)

        changePassword.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChangePasswordActivity::class.java)
                it.startActivity(intent)
            }
        }
        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
            activity?.finish()
        }
    }
}
