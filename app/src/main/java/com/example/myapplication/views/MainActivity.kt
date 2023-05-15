package com.example.myapplication.views

import android.app.Notification.Action
import android.companion.CompanionDeviceManager
import android.companion.CompanionDeviceService
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.myapplication.R
import com.example.myapplication.adapters.BannerAdapter
import com.example.myapplication.adapters.GridListAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.Banner
import com.example.myapplication.models.CoffeeModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference

    lateinit var sharedPreferences: SharedPreferences

    companion object{
        lateinit var count: TextView
        lateinit var uid: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        database = Firebase.database
        reference = database.reference
        count = binding.count

        var list = mutableListOf<CoffeeModel>()
        var bannerList = mutableListOf<Banner>()

        /*database.getReference("caffe").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                getCoffeData(list, snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage()
            }

        })*/

        //get list caffe from database
        database.getReference("caffe").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val dataCaffe = postSnapshot.getValue<CoffeeModel>()
                    if (dataCaffe != null) {
                        list.add(
                            CoffeeModel(
                                name = dataCaffe.name,
                                price = dataCaffe.price,
                                coffee = dataCaffe.coffee
                            )
                        )
                    }
                    binding.recyclerView.adapter = GridListAdapter(list)
                    binding.progress.visibility = View.GONE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        //open map application
        binding.constraintLayout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.hello_title))
            alertDialog.setMessage(getString(R.string.want_to_view_our_location))
            alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                val uri = String.format(Locale.ENGLISH, "geo:%f,%f",53.88456795766346, 27.5403940306815)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
            alertDialog.setNegativeButton(getString(R.string.no)) { dialog, _ -> /*alertDialog*/
            }
            alertDialog.show()

        }

        //init database banner
        database.getReference("banner").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                bannerData(bannerList, snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                bannerList.clear()
                bannerData(bannerList, snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                bannerList.clear()
                bannerData(bannerList, snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                bannerList.clear()
                bannerData(bannerList, snapshot)
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

    private fun bannerData(
        bannerList: MutableList<Banner>,
        snapshot: DataSnapshot
    ) {
        val banner = snapshot.getValue<Banner>()
        if (banner != null) {
            bannerList.add(Banner(link = banner.link))
        }
        binding.viewPager2.adapter = BannerAdapter(bannerList)
        binding.viewPager2.clipChildren = false
        binding.viewPager2.clipToPadding = false

        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        /*val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))*/
    }

    private fun showToastMessage() {
        Toast.makeText(this@MainActivity, R.string.error_text, Toast.LENGTH_SHORT).show()
    }

    //delete all list cart
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Important!")
        alertDialog.setMessage("You have added items to your cart. Are you sure you want to leave?")
        alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            val uid = sharedPreferences.getString("auth", "").toString()
            database.reference.child("product_list")
                .child(uid).removeValue()
            finish()
        }
        alertDialog.setNegativeButton(getString(R.string.no)) { dialog, _ -> /*alertDialog*/
        }
        alertDialog.show()
    }
}