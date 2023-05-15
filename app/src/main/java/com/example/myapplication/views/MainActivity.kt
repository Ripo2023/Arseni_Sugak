package com.example.myapplication.views

import android.app.Notification.Action
import android.companion.CompanionDeviceManager
import android.companion.CompanionDeviceService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database
        reference = database.reference

        var list = mutableListOf<CoffeeModel>()
        var bannerList = mutableListOf<Banner>()

        /*database.getReference("caffe").addChildEventListener(object : ChildEventListener {
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

        })*/

        binding.constraintLayout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.hello_title))
            alertDialog.setMessage(getString(R.string.want_to_view_our_location))
            alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                /*val uri = String.format(Locale.ENGLISH, "geo:%f,%f",53.88456795766346, 27.5403940306815)
                val intent = Intent.getIntent(View.ACT)*/
            }
            alertDialog.setNegativeButton(getString(R.string.no)) { dialog, _ -> /*alertDialog*/
            }
            alertDialog.show()

        }

        database.getReference("banner").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                bannerData(bannerList, snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                bannerData(bannerList, snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                bannerData(bannerList, snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                bannerData(bannerList, snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage()
            }

        })

        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))

        binding.user.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun bannerData(
        bannerList: MutableList<Banner>,
        snapshot: DataSnapshot
    ) {
        bannerList.clear()
        val banner = snapshot.getValue<Banner>()
        if (banner != null) {
            bannerList.add(Banner(link = banner.link))
        }
        binding.viewPager2.adapter = BannerAdapter(bannerList)
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