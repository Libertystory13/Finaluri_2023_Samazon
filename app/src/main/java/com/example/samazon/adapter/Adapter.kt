package com.example.samazon.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samazon.R
import com.example.samazon.data.ProductInfo
import com.google.firebase.storage.FirebaseStorage


class Adapter(private val productList:ArrayList<ProductInfo>): RecyclerView.Adapter<Adapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val currentItem = productList[position]
        val fileName = currentItem.fileName
        val ref = FirebaseStorage.getInstance().getReference("/ProductImages/${fileName}")
        ref.downloadUrl.addOnSuccessListener {
            Glide.with(holder.fileName.context).load(it).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(holder.fileName)
        }
        holder.productName.text = "სახელი: ${currentItem.name}"
        holder.productPrice.text = "ფასი: ${currentItem.price} ₾"
        holder.productDescription.text = "აღწერა: ${currentItem.description}"

    }

    override fun getItemCount(): Int {

        return productList.size

    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productName : TextView = itemView.findViewById(R.id.name)
        val productPrice: TextView = itemView.findViewById(R.id.price)
        val productDescription: TextView = itemView.findViewById(R.id.description)
        val fileName : ImageView = itemView.findViewById(R.id.productsImage)

    }
}