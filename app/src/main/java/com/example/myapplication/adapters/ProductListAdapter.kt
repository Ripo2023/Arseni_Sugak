package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.ProductListModel

class ProductListAdapter(
    val list: List<ProductListModel>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_product)
        val price: TextView = itemView.findViewById(R.id.price_product)
        val volume: TextView = itemView.findViewById(R.id.volume_product)
        val image: ImageView = itemView.findViewById(R.id.images)
        val ingredient: TextView = itemView.findViewById(R.id.ingredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.price.text = list[position].price
        holder.volume.text = list[position].volume
        holder.ingredient.text = list[position].ingredient
    }
}