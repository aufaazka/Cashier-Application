package com.example.uklcafe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomOpenHelper

@Database(
    entities = [
        Meja::class,
        Menu::class,
        User::class,
        Transaksi::class,
        DetailTransaksi::class
    ],
    version = 1
)

abstract class CafeDatabase : RoomDatabase() {
    abstract fun cafeDao(): CafeDao

    companion object{
        private var instance: CafeDatabase? = null

        fun getInstance(context: Context): CafeDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}