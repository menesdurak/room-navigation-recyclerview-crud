package com.menesdurak.roomnavigationrecyclerviewcrud.fragments.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firtName: String,
    val lastName: String,
    val age: Int
)