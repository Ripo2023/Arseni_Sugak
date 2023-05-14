package com.example.myapplication.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.ProductListModel
import com.example.myapplication.views.SelectIngredientActivity

class ProductListAdapter(
    val list: MutableList<ProductListModel>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_product)
        val price: TextView = itemView.findViewById(R.id.price_product)
        val volume: TextView = itemView.findViewById(R.id.volume_product)
        val image: ImageView = itemView.findViewById(R.id.images)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val edit: ImageView = itemView.findViewById(R.id.edit)
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

        holder.delete.setOnClickListener {
            var alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle(holder.itemView.context.getString(R.string.hello_title))
            alertDialog.setMessage(holder.itemView.context.getString(R.string.are_you_sure_you_want_to_delete_this_item))
            //POSITIVE
            //NEGATIVE

            list.removeAt(position)
        }

        holder.edit.setOnClickListener {
            //INTENT
        }
    }
}