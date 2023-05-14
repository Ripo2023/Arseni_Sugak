package com.example.myapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.adapters.GridListAdapter
import com.example.myapplication.adapters.ProductListAdapter
import com.example.myapplication.databinding.ActivityProductListBinding
import com.example.myapplication.models.CoffeeModel
import com.example.myapplication.models.ProductListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database
        auth = Firebase.auth
        reference.database.reference

        var list = mutableListOf<ProductListModel>()

        database.getReference("product_list/${auth.currentUser?.uid}").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                getProductListData(list, snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                getProductListData(list, snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                getProductListData(list, snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                getProductListData(list, snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage()
            }

        })

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun getProductListData(list: MutableList<ProductListModel>, snapshot: DataSnapshot) {
        list.clear()
        for (data in snapshot.children) {
            val dataProduct: ProductListModel? = data.getValue<ProductListModel>()
            if (dataProduct != null) {
                list.add(dataProduct)
            }
        }
        binding.recyclerView.adapter = ProductListAdapter(list)
    }

    private fun showToastMessage() {
        Toast.makeText(this@ProductListActivity, R.string.error_text, Toast.LENGTH_SHORT).show()
    }
}