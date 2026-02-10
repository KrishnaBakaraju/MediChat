package com.medichat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_history")
data class MedicalHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symptoms: String,
    val diagnosis: String,
    val treatment: String,
    val recommendation: String,
    val createdAt: Long
)
