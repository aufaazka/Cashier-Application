package com.example.uklcafe.transaksi;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.uklcafe.R
import com.example.uklcafe.database.CafeDatabase

class EditTransaksiActivity : AppCompatActivity() {
    lateinit var inputNamaPelanggan: EditText
    lateinit var spinnerMeja: Spinner
    lateinit var simpanButton: Button
    lateinit var dibayar: CheckBox

    lateinit var db: CafeDatabase
    var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaksi)

        inputNamaPelanggan = findViewById(R.id.namaPelanggan)
        spinnerMeja = findViewById(R.id.spinnerMeja)
        simpanButton = findViewById(R.id.checkOut)
        dibayar = findViewById(R.id.dibayar)


        db = CafeDatabase.getInstance(applicationContext)


        setDataSpinner()
        var id_transaksi: Int? = null
        id_transaksi = intent.getIntExtra("ID", 0)



        simpanButton.setOnClickListener{
            var status = "Belum Dibayar"
            if(dibayar.isChecked){
                status = "Dibayar"
            }
            if(inputNamaPelanggan.text.toString().isNotEmpty()){
                db.cafeDao().updateTransaksi(
                    inputNamaPelanggan.text.toString(),
                    db.cafeDao().getIdMejaFromNama(spinnerMeja.selectedItem.toString()),
                    status,
                    id_transaksi
                )
                finish()
            }

        }
    }

    private fun setDataSpinner(){
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, db.cafeDao().getAllNamaMeja())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMeja.adapter = adapter
    }
}