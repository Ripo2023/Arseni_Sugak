package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.adapters.GridListAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.CoffeeModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database
        reference.database.reference

        var list = mutableListOf<CoffeeModel>()

        database.getReference("caffe").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                getCoffeData(list, snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                getCoffeData(list, snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                getCoffeData(list, snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                getCoffeData(list, snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage()
            }

        })

        binding.user.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun showToastMessage() {
        Toast.makeText(this@MainActivity, R.string.error_text, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Important!")
        alertDialog.setMessage("You have added items to your cart. Are you sure you want to leave?")
    }

    private fun getCoffeData(
        list: MutableList<CoffeeModel>,
        snapshot: DataSnapshot
    ) {
        list.clear()
        for (data in snapshot.children) {
            val dataCaffe: CoffeeModel? = data.getValue<CoffeeModel>()
            if (dataCaffe != null) {
                list.add(dataCaffe)
            }
        }

        binding.recyclerView.adapter = GridListAdapter(list)
    }
}