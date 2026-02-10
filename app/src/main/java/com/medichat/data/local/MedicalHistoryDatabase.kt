package com.medichat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MedicalHistoryEntity::class], version = 1, exportSchema = false)
abstract class MedicalHistoryDatabase : RoomDatabase() {
    abstract fun medicalHistoryDao(): MedicalHistoryDao

    companion object {
        fun create(context: Context): MedicalHistoryDatabase {
            return Room.databaseBuilder(
                context,
                MedicalHistoryDatabase::class.java,
                "medichat.db"
            ).build()
        }
    }
}
