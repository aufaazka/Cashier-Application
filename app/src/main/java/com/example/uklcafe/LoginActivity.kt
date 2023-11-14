package com.example.uklcafe;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uklcafe.database.CafeDatabase
import com.example.uklcafe.database.User

class LoginActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        loginButton.setOnClickListener {
            if (editEmail.text.toString().isNotEmpty() && editPassword.text.toString().isNotEmpty()){
                var list: List<User> = db.cafeDao().login(editEmail.text.toString(), editPassword.text.toString())
                if (list.size > 0){
                    val moveIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    val name = list[0].nama
                    val role = list[0].role
                    val id_user = list[0].id_user
                    moveIntent.putExtra("name", name)
                    moveIntent.putExtra("role", role)
                    moveIntent.putExtra("id_user", id_user)
                    startActivity(moveIntent)
                }
                else{
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerButton.setOnClickListener{
            val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    fun init(){
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        loginButton = findViewById(R.id.btnLogin)
        registerButton = findViewById(R.id.btnRegister)

        db = CafeDatabase.getInstance(applicationContext)
    }
}