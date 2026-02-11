package com.medichat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MedicalHistoryEntity)

    @Query("SELECT * FROM medical_history ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<MedicalHistoryEntity>>
}
