package com.example.job2nsda

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val department: String,
    val salary: Double,
    val email: String,
    val createdAt: Long = System.currentTimeMillis()
)
