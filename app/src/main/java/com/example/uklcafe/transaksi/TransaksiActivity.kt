package com.example.uklcafe.transaksi;

import com.example.uklcafe.adapter.ItemAdapter
import com.example.uklcafe.database.CafeDatabase
import com.example.uklcafe.database.Menu
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklcafe.R

class TransaksiActivity : AppCompatActivity() {
    lateinit var recyclerMakanan: RecyclerView
    lateinit var recyclerMinuman: RecyclerView

    lateinit var adapterMakanan: ItemAdapter
    lateinit var adapterMinuman: ItemAdapter

    lateinit var db: CafeDatabase
    lateinit var checkoutButton: Button

    private var listMakanan = mutableListOf<Menu>()
    private var listMinuman = mutableListOf<Menu>()

    private var listCart = arrayListOf<Int?>()

    var nama: String = ""
    var role: String = ""
    var id_user: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        recyclerMakanan = findViewById(R.id.recyclerMakanan)
        recyclerMinuman = findViewById(R.id.recyclerMinuman)
        checkoutButton = findViewById(R.id.checkOut)

        db = CafeDatabase.getInstance(applicationContext)
        adapterMakanan = ItemAdapter(listMakanan)
        adapterMakanan.onAddClick = {
            listCart.add(it.id_menu)
            checkoutButton.isEnabled = true
            checkoutButton.visibility = View.VISIBLE
            checkoutButton.text = "Checkout (" + listCart.size + ")"
        }
        adapterMinuman = ItemAdapter(listMinuman)
        adapterMinuman.onAddClick = {
            listCart.add(it.id_menu)
            checkoutButton.isEnabled = true
            checkoutButton.visibility = View.VISIBLE
            checkoutButton.text = "Checkout (" + listCart.size + ")"
        }
        adapterMakanan.onSubstractClick = {
            listCart.remove(it.id_menu)
            if(listCart.size == 0){
                checkoutButton.isEnabled = false
                checkoutButton.visibility = View.INVISIBLE
            }
            checkoutButton.text = "Checkout (" + listCart.size + ")"
        }
        checkoutButton.isEnabled = false
        checkoutButton.visibility = View.INVISIBLE

        adapterMinuman.onSubstractClick = {
            listCart.remove(it.id_menu)
            if(listCart.size == 0){
                checkoutButton.isEnabled = false
                checkoutButton.visibility = View.INVISIBLE
            }
            checkoutButton.text = "Checkout (" + listCart.size + ")"
        }

        recyclerMakanan.adapter = adapterMakanan
        recyclerMakanan.layoutManager = LinearLayoutManager(this)
        recyclerMinuman.adapter = adapterMinuman
        recyclerMinuman.layoutManager = LinearLayoutManager(this)


        checkoutButton.setOnClickListener{
            val moveIntent = Intent(this@TransaksiActivity, CartActivity::class.java)
            moveIntent.putIntegerArrayListExtra("CART", listCart)
            moveIntent.putExtra("id_user", id_user)
            startActivity(moveIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getMenu()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getMenu() {
        listMakanan.clear()
        listMinuman.clear()
        listMakanan.addAll(db.cafeDao().getMenuFilterJenis("Makanan"))
        listMinuman.addAll(db.cafeDao().getMenuFilterJenis("Minuman"))
        adapterMakanan.notifyDataSetChanged()
        adapterMinuman.notifyDataSetChanged()
    }

}