package com.example.uklcafe.menu

import com.example.uklcafe.database.CafeDatabase
import com.example.uklcafe.database.Menu
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklcafe.R
import com.example.uklcafe.SwipeGesture
import com.example.uklcafe.adapter.ItemAddAdapter


class ListMenuActivity : AppCompatActivity() {
    lateinit var recyclerMakanan: RecyclerView
    lateinit var recyclerMinuman: RecyclerView

    lateinit var adapterMakanan: ItemAddAdapter
    lateinit var adapterMinuman: ItemAddAdapter

    lateinit var db: CafeDatabase
    lateinit var addButton: ImageButton


    private var listMakanan = mutableListOf<Menu>()
    private var listMinuman = mutableListOf<Menu>()

    private var listCart = arrayListOf<Int?>()

    var nama: String = ""
    var role: String = ""
    var id_user: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_menu)

        recyclerMakanan = findViewById(R.id.recyclerMakanan)
        recyclerMinuman = findViewById(R.id.recyclerMinuman)
        addButton = findViewById(R.id.buttonAdd)



        db = CafeDatabase.getInstance(applicationContext)
        adapterMakanan = ItemAddAdapter(listMakanan)

        adapterMinuman = ItemAddAdapter(listMinuman)


        recyclerMakanan.adapter = adapterMakanan
        recyclerMakanan.layoutManager = LinearLayoutManager(this)
        recyclerMinuman.adapter = adapterMinuman
        recyclerMinuman.layoutManager = LinearLayoutManager(this)

        swipeToGesture(recyclerMakanan)
        swipeToGesture(recyclerMinuman)



        addButton.setOnClickListener{
            val moveIntent = Intent(this@ListMenuActivity, AddItemActivity::class.java)
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
    private fun swipeToGesture(itemRv: RecyclerView){
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try{
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            var adapter: ItemAddAdapter = itemRv.adapter as ItemAddAdapter
                            db.cafeDao().deleteMenu(adapter.getItem(position))
                            adapter.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            val moveIntent = Intent(this@ListMenuActivity, EditMenuActivity::class.java)
                            var adapter: ItemAddAdapter = itemRv.adapter as ItemAddAdapter
                            var menu = adapter.getItem(position)
                            moveIntent.putExtra("ID", menu.id_menu)
                            moveIntent.putExtra("nama_menu", menu.nama_menu)
                            moveIntent.putExtra("harga_menu", menu.harga)
                            moveIntent.putExtra("jenis", menu.jenis)
                            startActivity(moveIntent)
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}