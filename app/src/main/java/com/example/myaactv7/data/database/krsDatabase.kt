package com.example.myaactv7.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myaactv7.data.entity.Mahasiswa


@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class krsDatabase : RoomDatabase() {

    abstract  fun mahasiswaDao(): Mahasiswa

    companion object{
        private var Instance: krsDatabase? = null

        fun getDatabase(context: Context):krsDatabase{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    krsDatabase::class.java,
                    "KrsDatabase"
                )
                    .build().also { Instance = it}
            })
        }
    }


}