package com.example.uklcafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.uklcafe.meja.ListMejaActivity
import com.example.uklcafe.menu.ListMenuActivity
import com.example.uklcafe.transaksi.ListTransaksiActivity
import com.example.uklcafe.transaksi.TransaksiActivity
import com.google.android.material.card.MaterialCardView

class DashboardActivity : AppCompatActivity() {

    private lateinit var CVMenu :MaterialCardView
    private lateinit var CVMeja :MaterialCardView
    private lateinit var CVDataTransaksi :MaterialCardView
    private lateinit var CVTransaksi :MaterialCardView
    private lateinit var role : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        CVMenu = findViewById(R.id.CVMenu)
        CVMeja = findViewById(R.id.CVMeja)
        CVDataTransaksi = findViewById(R.id.CVDataTransaksi)
        CVTransaksi = findViewById(R.id.CVTransaksi)

        role = intent.getStringExtra("role").toString()
        if(role == "Manager"){
            val moveIntent = Intent(this@DashboardActivity, ListTransaksiActivity::class.java)
            startActivity(moveIntent)
            CVMenu.isEnabled = false
            CVMenu.visibility = View.INVISIBLE

            CVMeja.isEnabled = false
            CVMeja.visibility = View.INVISIBLE

            CVTransaksi.isEnabled = false
            CVTransaksi.visibility = View.INVISIBLE
        }else if(role == "Kasir"){
            val moveIntent = Intent(this@DashboardActivity, TransaksiActivity::class.java)
            startActivity(moveIntent)
            CVMenu.isEnabled = false
            CVMenu.visibility = View.INVISIBLE

            CVMeja.isEnabled = false
            CVMeja.visibility = View.INVISIBLE

        }else{
            Toast.makeText(this, "Anda adalah admin", Toast.LENGTH_SHORT)
        }

        CVMenu.setOnClickListener{
            val intent1 = Intent (this@DashboardActivity, ListMenuActivity::class.java)
            startActivity(intent1)
        }
        CVMeja.setOnClickListener{
            val intent2 = Intent (this@DashboardActivity, ListMejaActivity::class.java)
            startActivity(intent2)
        }
        CVDataTransaksi.setOnClickListener{
            val intent3 = Intent (this@DashboardActivity, ListTransaksiActivity::class.java)
            startActivity(intent3)
        }
        CVTransaksi.setOnClickListener{
            val intent4 = Intent (this@DashboardActivity, TransaksiActivity::class.java)
            startActivity(intent4)
        }


    }
}