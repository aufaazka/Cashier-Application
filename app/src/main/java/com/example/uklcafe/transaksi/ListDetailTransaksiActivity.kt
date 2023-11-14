package com.example.uklcafe.transaksi;

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklcafe.R
import com.example.uklcafe.adapter.DetailAdapter
import com.example.uklcafe.adapter.ItemAdapter
import com.example.uklcafe.adapter.ItemAddAdapter
import com.example.uklcafe.database.CafeDatabase
import com.example.uklcafe.database.DetailTransaksi
import com.example.uklcafe.menu.AddItemOnDetailActivity

class ListDetailTransaksiActivity : AppCompatActivity() {
    lateinit var buttonAdd: ImageButton
    lateinit var recycler: RecyclerView
    lateinit var total: TextView
    lateinit var bayar: Button

    lateinit var adapter: DetailAdapter
    private var listDetail = arrayListOf<DetailTransaksi>()
    private var id_transaksi = 0
    private var id_meja = 0
    private var totalHarga = 0

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail_transaksi)

        buttonAdd = findViewById(R.id.buttonAdd)
        recycler = findViewById(R.id.recyclerDetail)
        total = findViewById(R.id.total)
        bayar = findViewById(R.id.btnBayar)

        id_transaksi = intent.getIntExtra("id_transaksi", 0)

        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = DetailAdapter(listDetail)
        recycler.adapter = adapter

        if(db.cafeDao().getTransaksi(id_transaksi).status == "Dibayar"){
            bayar.isEnabled = false
            bayar.visibility = View.INVISIBLE
        }
        buttonAdd.isEnabled = false
        buttonAdd.visibility = View.INVISIBLE


        buttonAdd.setOnClickListener{
            val moveIntent = Intent(this@ListDetailTransaksiActivity, AddItemOnDetailActivity::class.java)
            moveIntent.putExtra("id_transaksi", id_transaksi)
            startActivity(moveIntent)
        }

        bayar.setOnClickListener{
            val moveIntent = Intent(this@ListDetailTransaksiActivity, ListDetailTransaksiActivity::class.java)
            db.cafeDao().updateStatusTransaksi("Dibayar", id_transaksi)
            startActivity(moveIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getDetail()
        totalHarga = 0
        for (i in listDetail){
            totalHarga += i.harga
        }
        total.text = "Rp." + totalHarga.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getDetail(){
        listDetail.clear()
        listDetail.addAll(db.cafeDao().getDetailTransaksi(id_transaksi))
        adapter.notifyDataSetChanged()
    }
}