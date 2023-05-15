package com.example.myapplication.adapters

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.CoffeeModel
import com.example.myapplication.models.ProductListModel
import com.example.myapplication.models.User
import com.example.myapplication.views.MainActivity
import com.example.myapplication.views.SelectIngredientActivity
import com.example.myapplication.views.VerifyActivity
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class GridListAdapter(
    var list: List<CoffeeModel>
) : RecyclerView.Adapter<GridListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val button: TextView = itemView.findViewById(R.id.button)
        val image: ImageView = itemView.findViewById(R.id.image_caffe)

        val plus: TextView = itemView.findViewById(R.id.plus)
        val minus: TextView = itemView.findViewById(R.id.minus)
        val count: TextView = itemView.findViewById(R.id.count)
        val glav: MaterialCardView = itemView.findViewById(R.id.glav)
        val pl: LinearLayout = itemView.findViewById(R.id.pl_mn)
    }


    lateinit var database: FirebaseDatabase
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_caffe, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.price.text = list[position].price

        sharedPreferences =
            holder.itemView.context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        Glide.with(holder.itemView.context)
            .load(list[position].coffee)
            .into(holder.image)

        holder.plus.setOnClickListener {
            var count = list[position].count?.toInt()
            count = count!! + 1
            list[position].count = count.toString()
            holder.count.text = count.toString()
            MainActivity.count.text = (MainActivity.count.text.toString().toInt() + 1).toString()
        }
        holder.minus.setOnClickListener {
            var count = list[position].count?.toInt()
            if (count != 1) {
                count = count!! - 1
                list[position].count = count.toString()
                holder.count.text = count.toString()
                MainActivity.count.text =
                    (MainActivity.count.text.toString().toInt() - 1).toString()
            }
        }

        holder.itemView.setOnClickListener {
            if (list[position].check == true) {
                holder.button.visibility = View.VISIBLE
                holder.pl.visibility = View.GONE
                list[position].check = false
                holder.glav.strokeColor = holder.itemView.context.getColor(R.color.white)
                holder.glav.strokeWidth = 0
                list[position].count = "1"
                holder.count.text = "1"
                MainActivity.count.text = (MainActivity.count.text.toString()
                    .toInt() - (list[position].count)!!.toInt()).toString()
                if (MainActivity.count.text == "1") {
                    MainActivity.count.visibility = View.GONE
                }
            }
        }

        holder.button.setOnClickListener {
            /*holder.button.visibility = View.GONE
            holder.pl.visibility = View.VISIBLE
            MainActivity.count.visibility = View.VISIBLE
            MainActivity.count.visibility = View.VISIBLE
            list[position].check = list[position].check != true
            holder.glav.strokeColor = holder.itemView.context.getColor(R.color.orange)
            holder.glav.strokeWidth = 4
            Toast.makeText(holder.itemView.context, list[position].espanol, Toast.LENGTH_SHORT).show()
            MainActivity.count.text = (MainActivity.count.text.toString().toInt() + 1).toString()
            holder.count.text = "1"
*/
            database = Firebase.database

            database.reference.child("product_list")
                .child(sharedPreferences.getString("auth", "").toString()).child(
                list[position].name.toString()
            ).setValue(
                ProductListModel(
                    name = list[position].name,
                    price = list[position].price,
                    volume = "200 ml",
                    ingredient = "null",
                    coffe = list[position].coffee,
                )
            )

            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    SelectIngredientActivity::class.java
                )
            )
            SelectIngredientActivity.name = list[position].type.toString()
        }
    }
}