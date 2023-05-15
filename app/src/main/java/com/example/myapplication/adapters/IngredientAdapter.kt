package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.models.IngredientModel

class IngredientAdapter(
    private val list: List<IngredientModel>
) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_select_ingradient)
        val image: ImageView = itemView.findViewById(R.id.image_select_ingradient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_select_ingredient, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name

        Glide.with(holder.itemView.context)
            .load(list[position].coffe)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
    }
}