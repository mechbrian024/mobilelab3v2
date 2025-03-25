package com.example.pokai.pokaichao_comp304sec001_lab3.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,  // 3-digit ID (101-999)
    val name: String,
    val price: Double,
    val dateOfDelivery: String,
    val category: String,
    val isFavorite: Boolean
)