package com.example.myapplication.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.models.ProductListModel
import com.example.myapplication.views.SelectIngredientActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
        val count: TextView = itemView.findViewById(R.id.count)
    }


    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val koof  = when (list[position].volume){
            "200 ml" -> {
                1
            }
            "300 ml" -> {
                1.5
            }
            "400 ml" -> {
                2
            }

            else -> {1}
        }
        val price = list[position].price!!.toDouble() * list[position].count!!.toDouble() * koof.toDouble()
        holder.name.text = list[position].name
        holder.price.text = "from $price ₽"
        holder.volume.text = list[position].volume
        holder.ingredient.text = list[position].ingredient
        holder.count.text = list[position].count

        Glide.with(holder.itemView.context)
            .load(list[position].coffe)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        sharedPreferences =
            holder.itemView.context.getSharedPreferences("welcom", AppCompatActivity.MODE_PRIVATE)


        //delete items
        holder.delete.setOnClickListener {
            var alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle(holder.itemView.context.getString(R.string.hello_title))
            alertDialog.setMessage(holder.itemView.context.getString(R.string.are_you_sure_you_want_to_delete_this_item))
            alertDialog.setPositiveButton(holder.itemView.context.getString(R.string.yes)) { dialog, _ ->
                val database = Firebase.database
                val uid = sharedPreferences.getString("auth", "").toString()
                database.reference.child("product_list")
                    .child(uid).child(list[position].name.toString()).removeValue()
                list.removeAt(position)
            }
            alertDialog.setNegativeButton(holder.itemView.context.getString(R.string.no)) { dialog, _ -> /*alertDialog*/
            }
            alertDialog.show()
        }

        //edit items
        holder.edit.setOnClickListener {
            SelectIngredientActivity.name = list[position].type.toString()
            SelectIngredientActivity.naming = list[position].name.toString()
            holder.itemView.context.startActivity(Intent(holder.itemView.context, SelectIngredientActivity::class.java))
        }
    }
}